package f1_tracks.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrandPrix {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer round;
    private String grandPrix;
    private double length;
    private int turns;
    private Date trackrecord;
    private int numberOfDRSZones;
    private boolean clockwise;

}
