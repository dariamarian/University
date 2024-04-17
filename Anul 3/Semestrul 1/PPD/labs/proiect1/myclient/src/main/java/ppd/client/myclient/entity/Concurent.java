package ppd.client.myclient.entity;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Concurent {

    private String id;

    private Double score;

    private String country;

    @Override
    public String toString() {
        return "Concurent with id: " + id + " and score: " + score + " from country: " + country + "\n";
    }
}
