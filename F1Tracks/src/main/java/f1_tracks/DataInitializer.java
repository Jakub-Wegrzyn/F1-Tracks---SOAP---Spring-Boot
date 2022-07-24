package f1_tracks;

import f1_tracks.model.GrandPrix;
import f1_tracks.repo.GrandPrixRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(DataInitializer.class);

    private final GrandPrixRepository grandPrixRepositoryRepository;

    public void initData() throws Exception {

        GrandPrix t1 = GrandPrix.builder()
                .round(1)
                .grandPrix("Bahrain Grand Prix")
                .length(5.412)
                .turns(15)
                .trackrecord(new SimpleDateFormat("mm:ss:SSS").parse("01:31:447"))
                .numberOfDRSZones(3)
                .clockwise(true)
                .build();

        GrandPrix t2 = GrandPrix.builder()
                .round(2)
                .grandPrix("Saudi Arabian Grand Prix")
                .length(6.174)
                .turns(27)
                .trackrecord(new SimpleDateFormat("mm:ss:SSS").parse("01:30:734"))
                .numberOfDRSZones(3)
                .clockwise(true)
                .build();

        GrandPrix t3 = GrandPrix.builder()
                .round(3)
                .grandPrix("Australian Grand Prix")
                .length(5.278)
                .turns(14)
                .trackrecord(new SimpleDateFormat("mm:ss:SSS").parse("01:24:125"))
                .numberOfDRSZones(4)
                .clockwise(true)
                .build();

        GrandPrix t4 = GrandPrix.builder()
                .round(4)
                .grandPrix("Emilia Romagna Grand Prix")
                .length(4.909)
                .turns(20)
                .trackrecord(new SimpleDateFormat("mm:ss:SSS").parse("01:15:484"))
                .numberOfDRSZones(1)
                .clockwise(true)
                .build();

        GrandPrix t5 = GrandPrix.builder()
                .round(5)
                .grandPrix("Miami Grand Prix")
                .length(5.412)
                .turns(19)
                .trackrecord(new SimpleDateFormat("mm:ss:SSS").parse("01:31:361"))
                .numberOfDRSZones(3)
                .clockwise(true)
                .build();

        GrandPrix t6 = GrandPrix.builder()
                .round(6)
                .grandPrix("Azerbaijan Grand Prix")
                .length(6.003)
                .turns(20)
                .trackrecord(new SimpleDateFormat("mm:ss:SSS").parse("01:43:009"))
                .numberOfDRSZones(2)
                .clockwise(false)
                .build();

        GrandPrix t7 = GrandPrix.builder()
                .round(7)
                .grandPrix("Singapore Grand Prix")
                .length(5.063)
                .turns(23)
                .trackrecord(new SimpleDateFormat("mm:ss:SSS").parse("01:45:008"))
                .numberOfDRSZones(2)
                .clockwise(false)
                .build();

        grandPrixRepositoryRepository.saveAll(Arrays.asList(t1,t2,t3,t4,t5,t6,t7));
        LOG.info("Data initialized");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
