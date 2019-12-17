package mobildev.iosm.com.priot.Model;

public class CarteStatistics {
    private String AnneeMois,dure;

    public CarteStatistics(){

    }

    public CarteStatistics(String anneeMois, String dure) {
        AnneeMois = anneeMois;
        this.dure = dure;
    }

    public String getAnneeMois() {
        return AnneeMois;
    }

    public void setAnneeMois(String anneeMoi) {
        AnneeMois = anneeMoi;
    }

    public String getDure() {
        return dure;
    }

    public void setDure(String dure) {
        this.dure = dure;
    }
}
