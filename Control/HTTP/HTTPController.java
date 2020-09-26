package Control.HTTP;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.rmi.ConnectException;
import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("ALL")
@Deprecated
public class HTTPController {
    private final String login;
    private final String password;
    private String token;
    private HttpClient httpClient;

    public HTTPController(String login, String password) throws IOException, InterruptedException {
        this.login = login;
        this.password = password;
        this.httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).connectTimeout(Duration.ofSeconds(60)).build();
    }

    public void getToken() throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder().GET().uri(URI.create("http://127.0.0.1:5000/"+login+"/"+password)).build();
        HttpResponse<String> response = httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
        this.token = response.body();
    }


    public String oceny() throws IOException, InterruptedException, IllegalAccessException {
        LinkedList<String> list = new LinkedList<>();
        HttpResponse<String> response = httpClient.send(HttpRequest.newBuilder().GET().uri(URI.create("http://127.0.0.1:5000/"+token+"/"+"oceny")).build(),HttpResponse.BodyHandlers.ofString());
        String resp = response.body();
        if(resp.equals("")){
            throw new IllegalAccessException("NIEPRWIDLOWE HASLO LUB LOGIN");
        }

        return resp;
    }
    public List<String[]> klasa() throws IOException, InterruptedException {
        LinkedList<String[]> list = new LinkedList<>();
        HttpResponse<String> response = httpClient.send(HttpRequest.newBuilder().GET().uri(URI.create("http://127.0.0.1:5000/"+token+"/"+"klasa")).build(),HttpResponse.BodyHandlers.ofString());
        String resp = response.body();
        for(String oc : resp.split("\\|\\|")){
            list.add(oc.split("\t"));
        }
        return list;
    }
    public List<String> plan() throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.send(HttpRequest.newBuilder().GET().uri(URI.create("http://127.0.0.1:5000/"+token+"/"+"plan")).build(),HttpResponse.BodyHandlers.ofString());
        String[] resp = response.body().split("\\|\\|");
        List<String> plan = new LinkedList(Arrays.asList(resp));
        return plan;
    }
    public List<List<String>> testy() throws IOException, InterruptedException{
        List<List<String>> dates = new LinkedList<>();
        HttpResponse<String> response = httpClient.send(HttpRequest.newBuilder().GET().uri(URI.create("http://127.0.0.1:5000/"+token+"/"+"testy")).build(),HttpResponse.BodyHandlers.ofString());
        for(String resp : response.body().split("\\n")){
            List<String> list = new LinkedList<>();
            String[] resps = resp.split("\\|\\|");
            list.add(resps[0]);
            for(String res : resps[1].split("                                                   ")){
                list.add(res);
            }
            dates.add(list);
        }
        return dates;
    }

    public void close() throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.send(HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString("")).uri(URI.create("http://127.0.0.1:5000/"+token+"/"+"close")).build(),HttpResponse.BodyHandlers.ofString());
        if(!response.body().equals("OK")){
            throw new ConnectException("NIE UDALO SIĘ ZAKONCZYC ZADANIA");
        }
    }

}