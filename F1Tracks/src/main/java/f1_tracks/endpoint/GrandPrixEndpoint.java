package f1_tracks.endpoint;

import f1_tracks.config.SoapWSConfig;
import f1_tracks.model.GrandPrix;
import f1_tracks.repo.GrandPrixRepository;
import edu.pja.sri.jwegrzyn.sri4f1tracks.grandprix.*;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Endpoint
@RequiredArgsConstructor
public class GrandPrixEndpoint {

    private final GrandPrixRepository grandPrixRepository;

    @PayloadRoot(namespace = SoapWSConfig.GRANDPRIX_NAMESPACE, localPart = "getGrandPrixRequest")
    @ResponsePayload
    public GetGrandPrixResponse getGrandPrix(@RequestPayload GetGrandPrixRequest req) {
        List<GrandPrix> grandPrixList = grandPrixRepository.findAll();
        List<GrandprixDto> dtoList = grandPrixList.stream()
                .map(this :: convertToDto)
                .collect(Collectors.toList());
        GetGrandPrixResponse res = new GetGrandPrixResponse();
        res.getGrandPrix().addAll(dtoList);
        return res;
    }

    @PayloadRoot(namespace = SoapWSConfig.GRANDPRIX_NAMESPACE, localPart = "getGrandPrixByRoundRequest")
    @ResponsePayload
    public GetGrandPrixByRoundResponse getGrandPrixByRound(@RequestPayload GetGrandPrixByRoundRequest request){
        int grandprixround = request.getRound();
        Optional<GrandPrix> grandPrixOptional = grandPrixRepository.findById(grandprixround);
        GetGrandPrixByRoundResponse response = new GetGrandPrixByRoundResponse();
        response.setGrandPrix(convertToDto(grandPrixOptional.orElse(null)));
        return response;
    }

    @PayloadRoot(namespace = SoapWSConfig.GRANDPRIX_NAMESPACE, localPart = "addGrandPrixRequest")
    @ResponsePayload
    public AddGrandPrixResponse addGrandPrix(@RequestPayload AddGrandPrixRequest req) throws ParseException {
        GrandprixDto grandprixDto = req.getGrandPrix();
        GrandPrix grandPrixCircuits2022 = convertToEntity(grandprixDto);
        grandPrixRepository.save(grandPrixCircuits2022);
        AddGrandPrixResponse response = new AddGrandPrixResponse();
        response.setRound(grandPrixCircuits2022.getRound());
        return response;
    }

    @PayloadRoot(namespace = SoapWSConfig.GRANDPRIX_NAMESPACE, localPart = "deleteGrandPrixByRoundRequest")
    @ResponsePayload
    public DeleteGrandPrixByRoundResponse deleteGrandPrixByRound(@RequestPayload DeleteGrandPrixByRoundRequest deleteGrandPrixRequest){
        int grandprixround = deleteGrandPrixRequest.getRound();
        List<Optional<GrandPrix>> temporaryList = new ArrayList<>();
        temporaryList.add(grandPrixRepository.findById(grandprixround));

        grandPrixRepository.deleteById(grandprixround);

        DeleteGrandPrixByRoundResponse response = new DeleteGrandPrixByRoundResponse();
        response.setGrandPrix(convertToDto(temporaryList.get(0).orElse(null)));
        return response;
    }

    @PayloadRoot(namespace = SoapWSConfig.GRANDPRIX_NAMESPACE, localPart = "getGrandPrixByClockWiseRequest")
    @ResponsePayload
    public GetGrandPrixByClockWiseResponse getGrandPrixByClockWise(@RequestPayload GetGrandPrixByClockWiseRequest getGrandPrixByClockWise){
        List<GrandPrix> grandPrixList = grandPrixRepository.findByClockwise(getGrandPrixByClockWise.isClockwise());
        List<GrandprixDto> dtoList = grandPrixList.stream()
                .map(this :: convertToDto)
                .collect(Collectors.toList());
        GetGrandPrixByClockWiseResponse response= new GetGrandPrixByClockWiseResponse();
        response.getGrandPrix().addAll(dtoList);
        return response;
    }

    @PayloadRoot(namespace = SoapWSConfig.GRANDPRIX_NAMESPACE, localPart = "orderGrandPrixByLengthRequest")
    @ResponsePayload
    public OrderGrandPrixByLengthResponse orderGrandPrixByLength(@RequestPayload OrderGrandPrixByLengthRequest request){
        List<GrandPrix> grandPrixList = grandPrixRepository.findAll();
        List<GrandPrix> sortedListbyLength = grandPrixList.stream().sorted(Comparator.comparingDouble(GrandPrix::getLength).reversed()).collect(Collectors.toList());
        List<GrandprixDto> dtoList = sortedListbyLength.stream()
                .map(this :: convertToDto)
                .collect(Collectors.toList());
        OrderGrandPrixByLengthResponse res = new OrderGrandPrixByLengthResponse();
        res.getGrandPrix().addAll(dtoList);
        return res;
    }


    private GrandprixDto convertToDto(GrandPrix e){
        if(e == null){
            return null;
        }
        GrandprixDto dto = new GrandprixDto();
        dto.setRound(e.getRound());
        dto.setGrandPrix(e.getGrandPrix());
        dto.setLength(e.getLength());
        dto.setTurns(e.getTurns());
        dto.setTrackrecord(e.getTrackrecord().toString().substring(13,23));
        dto.setNumberOfDRSZones(e.getNumberOfDRSZones());
        dto.setClockwise(e.isClockwise());
        return dto;
    }

    private GrandPrix convertToEntity(GrandprixDto dto) throws ParseException {
        return GrandPrix.builder()
                .round(dto.getRound())
                .grandPrix(dto.getGrandPrix())
                .length(dto.getLength())
                .turns(dto.getTurns())
                .trackrecord(new SimpleDateFormat("mm:ss:SSS").parse(dto.getTrackrecord()))
                .numberOfDRSZones(dto.getNumberOfDRSZones())
                .clockwise(dto.isClockwise())
                .build();
    }

}
