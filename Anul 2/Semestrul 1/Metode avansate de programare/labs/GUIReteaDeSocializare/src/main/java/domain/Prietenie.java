package domain;

import utils.Constants;

import java.time.LocalDateTime;

public class Prietenie extends Entity<Long> {
    private final long idUser1;
    private final long idUser2;
    private LocalDateTime friendsFrom=null;
    private String status;

    public Prietenie(long idUser1, long idUser2) {
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.status="PENDING";
    }

    public long getIdUser1() {
        return idUser1;
    }

    public long getIdUser2() {
        return idUser2;
    }

    public String getFriendsFrom(){
        if (friendsFrom==null)
            return "Waiting";
        return friendsFrom.format(Constants.DATE_TIME_FORMATTER);
    }

    public void setFriendsFrom(String time){
        //if (!time.isEmpty())
        if (!time.equals("Waiting"))
            friendsFrom=LocalDateTime.parse(time, Constants.DATE_TIME_FORMATTER);
        else friendsFrom=null;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void acceptFriend() {
        this.status = "ACCEPTED";
        friendsFrom=LocalDateTime.now();
    }
    @Override
    public void set(Entity other) {

    }

    @Override
    public String toString() {
        return "Prietenie{" +
                "idPrietenie=" + super.getId()+
                ", idUser1=" + idUser1 +
                ", idUser2=" + idUser2 +
                ", friendsFrom=" + getFriendsFrom() +
                '}';
    }
}