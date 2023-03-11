package domain;

public class UtilizatorDTOPrieten {
        private String friendsSince;
        private String name_user;
        private long UID;

        public UtilizatorDTOPrieten(Utilizator user,String friendsSince) {
            this.friendsSince = friendsSince;
            String name=user.getFirstName()+" "+ user.getLastName();
            this.name_user = name;
            UID= user.getId();
        }

        public long getUID() {
            return UID;
        }

        public String getFriendsSince() {
            return friendsSince;
        }

        public String getName_user() {
            return name_user;
        }
    }
