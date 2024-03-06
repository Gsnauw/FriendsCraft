package nl.friendscraft.friendscraft.Checks;

import java.util.UUID;

public class MaintenanceCheck {
    public class CheckResult {
        private String job;
        boolean result;

        private String tijd;
        public CheckResult(boolean result, String job, String tijd) {
            this.job = job;
            this.tijd = tijd;
            this.result = result;
        }
        public boolean getResult() {
            return result;
        }
        public String getJob() {
            return job;
        }
        public String getTijd() {
            return tijd;
        }
    }

    public CheckResult checkMaintenance(UUID playerUUID) {
        for (String blk : Janitor.janitordata) {
            String[] splitted = blk.split(":");
            String whitelidtUUID = splitted[0];
            if (playerUUID.equalsIgnoreCase(whitelidtUUID) && wereld.equalsIgnoreCase(worldNaam) && (blockLocatieX.equals(Xas)) && (blockLocatieY.equals(Yas)) && (blockLocatieZ.equals(Zas))) {
                return new CheckResult(true, "janitor", tijd);
            }
        }
        return new CheckResult(false, "geen", null);
    }
}
}
