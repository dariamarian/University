package proiectServerClientJava.network.protobuffprotocol;

import proiectServerClientJava.domain.*;

import java.util.ArrayList;
import java.util.List;

public class ProtoUtils {
    public static ClientProtobufs.AppRequest createLoginRequest(Angajat employee){
        ClientProtobufs.Angajat myEmployee = ClientProtobufs.Angajat.newBuilder().
                setId(employee.getId()).
                setName(employee.getName()).
                setUsername(employee.getUsername()).
                setPassword(employee.getPassword()).build();

        return ClientProtobufs.AppRequest.newBuilder().setType(ClientProtobufs.AppRequest.Type.Login)
                .setAngajat(myEmployee).build();
    }

    public static ClientProtobufs.AppRequest createLogoutRequest(Angajat angajat) {
        ClientProtobufs.Angajat angajatDTO = ClientProtobufs.Angajat.newBuilder().
                setId(angajat.getId()).
                setName(angajat.getName()).
                setUsername(angajat.getUsername()).
                setPassword(angajat.getPassword()).build();
        ClientProtobufs.AppRequest request = ClientProtobufs.AppRequest.newBuilder().setType(ClientProtobufs.AppRequest.Type.Logout)
                .setAngajat(angajatDTO).build();
        return request;
    }

    public static ClientProtobufs.AppRequest createAddBiletRequest(Bilet bilet) {
        ClientProtobufs.Bilet biletDTO = ClientProtobufs.Bilet.newBuilder()
                .setId(bilet.getId())
                .setCumparatorName(bilet.getCumparatorName())
                .setIdSpectacol(bilet.getIdSpectacol())
                .setNrSeats(bilet.getNrSeats()).build();
        return ClientProtobufs.AppRequest.newBuilder().setType(ClientProtobufs.AppRequest.Type.AddBilet)
                .setBilet(biletDTO).build();
    }

    public static ClientProtobufs.AppRequest createGetSpectacolRequest(Long id) {
        ClientProtobufs.AppRequest request = ClientProtobufs.AppRequest.newBuilder()
                .setType(ClientProtobufs.AppRequest.Type.GetSpectacol)
                .setIdSpectacol(id).build();
        return request;
    }

    public static ClientProtobufs.AppRequest createGetAllSpectacoleRequest() {
        return ClientProtobufs.AppRequest.newBuilder().setType(ClientProtobufs.AppRequest.Type.GetAllSpectacole).build();
    }

    public static Angajat getAngajat(ClientProtobufs.AppRequest request) {
        return new Angajat(request.getAngajat().getId(), request.getAngajat().getName(), request.getAngajat().getUsername(), request.getAngajat().getPassword());
    }

    public static Bilet getBilet(ClientProtobufs.AppRequest request) {
        return new Bilet(request.getBilet().getId(), request.getBilet().getCumparatorName(), request.getBilet().getIdSpectacol(), request.getBilet().getNrSeats());
    }

    public static Spectacol getSpectacol(ClientProtobufs.AppRequest request) {
        return new Spectacol(request.getSpectacol().getId(),
                request.getSpectacol().getArtistName(),
                request.getSpectacol().getDate(),
                request.getSpectacol().getTime(),
                request.getSpectacol().getPlace(),
                request.getSpectacol().getAvailableSeats(),
                request.getSpectacol().getSoldSeats());
    }

    public static ClientProtobufs.AppResponse createLoginResponse(Angajat angajat) {
        ClientProtobufs.Angajat angajatDTO = ClientProtobufs.Angajat.newBuilder().
                setId(angajat.getId()).
                setName(angajat.getName()).
                setUsername(angajat.getUsername()).
                setPassword(angajat.getPassword()).build();
        ClientProtobufs.AppResponse response = ClientProtobufs.AppResponse.newBuilder().setType(ClientProtobufs.AppResponse.Type.Ok)
                .setAngajat(angajatDTO).build();
        return response;
    }
    public static ClientProtobufs.AppResponse createAddBiletResponse(Bilet bilet) {
        ClientProtobufs.Bilet biletDTO = ClientProtobufs.Bilet.newBuilder().
                setId(bilet.getId())
                .setCumparatorName(bilet.getCumparatorName())
                .setIdSpectacol(bilet.getIdSpectacol())
                .setNrSeats(bilet.getNrSeats()).build();
        return ClientProtobufs.AppResponse.newBuilder().setType(ClientProtobufs.AppResponse.Type.SoldBilet)
                .setBilet(biletDTO).build();
    }

    public static ClientProtobufs.AppResponse createGetSpectacolResponse(Spectacol spectacol) {
        ClientProtobufs.Spectacol spectacolDTO = ClientProtobufs.Spectacol.newBuilder().
                setId(spectacol.getId())
                .setArtistName(spectacol.getArtistName())
                .setDate(spectacol.getDate())
                .setTime(spectacol.getTime())
                .setPlace(spectacol.getPlace())
                .setAvailableSeats(spectacol.getAvailableSeats())
                .setSoldSeats(spectacol.getSoldSeats())
                .build();
        ClientProtobufs.AppResponse response = ClientProtobufs.AppResponse.newBuilder()
                .setType(ClientProtobufs.AppResponse.Type.GetSpectacol)
                .setSpectacol(spectacolDTO).build();
        return response;
    }


    public static ClientProtobufs.AppResponse createGetAllSpectacoleResponse(Iterable<Spectacol> spectacole) {
        List<ClientProtobufs.Spectacol> spectacole2 = new ArrayList<>();
        spectacole.forEach(spectacol -> spectacole2.add(ClientProtobufs.Spectacol.newBuilder().setId(spectacol.getId()).
                setArtistName(spectacol.getArtistName()).
                setDate(spectacol.getDate()).
                setTime(spectacol.getTime()).
                setPlace(spectacol.getPlace()).
                setAvailableSeats(spectacol.getAvailableSeats()).
                setSoldSeats(spectacol.getSoldSeats()).
                build()));
        return ClientProtobufs.AppResponse.newBuilder().setType(ClientProtobufs.AppResponse.Type.GetAllSpectacole).addAllSpectacole(spectacole2).build();
    }

    public static ClientProtobufs.AppResponse createOkResponse() {
        ClientProtobufs.AppResponse response = ClientProtobufs.AppResponse.newBuilder()
                .setType(ClientProtobufs.AppResponse.Type.Ok).build();
        return response;
    }

    public static ClientProtobufs.AppResponse createErrorResponse(String text) {
        ClientProtobufs.AppResponse response = ClientProtobufs.AppResponse.newBuilder()
                .setType(ClientProtobufs.AppResponse.Type.Error)
                .setError(text).build();
        return response;
    }

    public static String getError(ClientProtobufs.AppResponse response) {
        String errorMessage = response.getError();
        return errorMessage;
    }


    public static Angajat getAngajat(ClientProtobufs.AppResponse response) {
        return new Angajat(response.getAngajat().getId(), response.getAngajat().getName(), response.getAngajat().getUsername(), response.getAngajat().getPassword());
    }

    public static Bilet getBilet(ClientProtobufs.AppResponse response) {
        return new Bilet(response.getBilet().getId(), response.getBilet().getCumparatorName(), response.getBilet().getIdSpectacol(), response.getBilet().getNrSeats());
    }

    public static Spectacol getSpectacol(ClientProtobufs.AppResponse response) {
        return new Spectacol(response.getSpectacol().getId(),
                response.getSpectacol().getArtistName(),
                response.getSpectacol().getDate(),
                response.getSpectacol().getTime(),
                response.getSpectacol().getPlace(),
                response.getSpectacol().getAvailableSeats(),
                response.getSpectacol().getSoldSeats());
    }

    public static Iterable <Spectacol> getAllSpectacole(ClientProtobufs.AppResponse response)
    {
        List <Spectacol> spectacole = new ArrayList<>();
        response.getSpectacoleList().forEach(spectacol -> spectacole.add(new Spectacol(spectacol.getId(),
                spectacol.getArtistName(), spectacol.getDate(), spectacol.getTime(),
                spectacol.getPlace(), spectacol.getAvailableSeats(), spectacol.getSoldSeats())));
        return spectacole;
    }

    public static long getIdSpectacol(ClientProtobufs.AppRequest request)
    {
        return request.getIdSpectacol();
    }
}
