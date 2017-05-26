package bit01.com.mx.echale.models;

/**
 * Created by ericklara on 24/05/17.
 */

public class Partido {
    private String localTeam;
    private String awayTeam;
    private String date;
    private String localTeamImageUrl;
    private String awayTeamImageUrl;
    private String time;


    public Partido() {
    }

    public Partido(String localTeam, String awayTeam, String date, String localTeamImageUrl, String awayTeamImageUrl, String time) {
        this.localTeam = localTeam;
        this.awayTeam = awayTeam;
        this.date = date;
        this.localTeamImageUrl = localTeamImageUrl;
        this.awayTeamImageUrl = awayTeamImageUrl;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocalTeam() {
        return localTeam;
    }

    public void setLocalTeam(String localTeam) {
        this.localTeam = localTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocalTeamImageUrl() {
        return localTeamImageUrl;
    }

    public void setLocalTeamImageUrl(String localTeamImageUrl) {
        this.localTeamImageUrl = localTeamImageUrl;
    }

    public String getAwayTeamImageUrl() {
        return awayTeamImageUrl;
    }

    public void setAwayTeamImageUrl(String awayTeamImageUrl) {
        this.awayTeamImageUrl = awayTeamImageUrl;
    }
}
