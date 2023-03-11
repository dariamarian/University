package network;

import domain.Prietenie;
import domain.Utilizator;
import exceptions.NetworkException;

import java.util.*;

public class ReteaUtilizator implements Retea {

    private final Map<Long, Vector<Long>> network = new HashMap<>();

    @Override
    public void add(Utilizator user) {
        network.put(user.getId(), new Vector<>());
    }

    @Override
    public void remove(Utilizator user) {
        long idToRemove = user.getId();
        for (Vector<Long> friends : network.values()) {
            friends.remove(idToRemove);
        }
        network.remove(idToRemove);
    }

    private Boolean friendshipExists(long entity1, long entity2) {
        Vector<Long> friends = network.get(entity1);
        for (long friendId : friends) {
            if (friendId == entity2) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addFriendship(Prietenie prietenie) throws NetworkException {
       // if (friendshipExists(prietenie.getIdUser1(), prietenie.getIdUser2())) {
        //    throw new NetworkException("Utilizatorii sunt deja prieteni.\n");
        //}
        network.get(prietenie.getIdUser1()).add(prietenie.getIdUser2());
        network.get(prietenie.getIdUser2()).add(prietenie.getIdUser1());
    }

    @Override
    public void removeFriendship(Prietenie prietenie) throws NetworkException {
        if (!friendshipExists(prietenie.getIdUser1(), prietenie.getIdUser2())) {
            throw new NetworkException("Utilizatorii nu sunt prieteni.\n");
        }
        network.get(prietenie.getIdUser1()).remove(prietenie.getIdUser2());
        network.get(prietenie.getIdUser2()).remove(prietenie.getIdUser1());
    }

    private int dfs(long[] users, long id, int communityNumber) {
        users[Math.toIntExact(id)] = communityNumber;
        int numberOfPath = 0;
        Vector<Long> friends = network.get(id);
        for (long idFriend : friends) {
            if (users[Math.toIntExact(idFriend)] == 0) {
                int path = dfs(users, idFriend, communityNumber);
                numberOfPath = Math.max(numberOfPath, path);
            }
        }
        return numberOfPath + 1;
    }

    private long getMaxId() {
        long max = 0L;
        for (long id : network.keySet()) {
            max = Math.max(max, id);
        }
        return max;
    }

    private long[] getCommunities() {
        long[] users = new long[(int) (getMaxId() + 1)];
        int communityNumber = 1;
        for (long id : network.keySet()) {
            if (users[Math.toIntExact(id)] == 0) {
                dfs(users, id, communityNumber);
                communityNumber = communityNumber + 1;
            }
        }
        return users;
    }

    private Map<Long, Integer> getPopulation() {
        long[] users = getCommunities();
        Map<Long, Integer> communitiesPopulation = new HashMap<>();

        for (int count = 1; count < users.length; count = count + 1) {
            if (users[count] == 0) {
                continue;
            }

            if (!communitiesPopulation.containsKey(users[count])) {
                communitiesPopulation.put(users[count], 0);
            }
            communitiesPopulation.put(users[count], communitiesPopulation.get(users[count]) + 1);
        }

        return communitiesPopulation;
    }

    @Override
    public int getNumberOfCommunities() {
        Map<Long, Integer> communitiesPopulation = getPopulation();

        return communitiesPopulation.size();
    }

    @Override
    public Vector<Long> getMostSociableCommunity() {
        long[] users = new long[(int) (getMaxId() + 1)];
        Vector<Long> biggestCommunity = new Vector<>();
        int communityNumber = 1;
        int maxPath = 0;
        int currentPath;
        long startNode = -1;
        for (long id : network.keySet()) {
            if (users[Math.toIntExact(id)] == 0) {
                currentPath = dfs(users, id, communityNumber);
                communityNumber = communityNumber + 1;
                if (currentPath > maxPath) {
                    maxPath = currentPath;
                    startNode = id;
                }
            }
        }
        Arrays.fill(users, 0);
        dfs(users, startNode, 1);

        for (int count = 1; count < users.length; count = count + 1) {
            if (users[count] != 1) {
                continue;
            }
            biggestCommunity.add((long) count);
        }

        return biggestCommunity;
    }
}
