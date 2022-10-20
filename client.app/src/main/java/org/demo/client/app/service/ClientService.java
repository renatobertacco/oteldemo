package org.demo.client.app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

import org.demo.client.app.dto.ClientDTO;
import org.demo.client.app.model.Client;
import org.demo.client.app.repo.ClientRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repo;

    @Autowired
    private ModelMapper modelMapper;

    public List<ClientDTO> findAll() {
        return apply((List<ClientDTO>) null, i -> repo.findAll());
    }

    public List<ClientDTO> findAll(Long id, String name, String email, String idCard, String phone) {
        Client client = new Client();
        client.setId(id);
        client.setName(name);
        client.setEmail(email);
        client.setIdCard(idCard);
        client.setPhone(phone);

        return apply((List<ClientDTO>) null, i -> repo.findAll(Example.of(client, ExampleMatcher.matchingAll()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase()))));
    }

    public ClientDTO findById(Long id) {
        Optional<Client> client = repo.findById(id);
        return client.map(b -> modelMapper.map(b, ClientDTO.class)).orElse(null);
    }

    public List<ClientDTO> findByIds(Collection<Long> ids) {
        return apply((List<ClientDTO>) null, i -> repo.findAllById(ids));
    }

    public ClientDTO create(ClientDTO client) {
        return apply(client, repo::save);
    }

    public List<ClientDTO> createAll(Collection<ClientDTO> clients) {
        return apply(clients, repo::saveAll);
    }

    public ClientDTO update(ClientDTO client) {
        return apply(client, repo::save);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public void createDemoData() {
        List<Client> clients = new ArrayList<>();
        List<String> names = Arrays.asList("Mario Rossi", "Luigi Bianchi", "Paolo Marchetti", "Fabio Ricci",
                "Bruno Bruno");
        ThreadLocalRandom generator = ThreadLocalRandom.current();
        for (String name : names) {
            Client client = new Client();
            client.setName(name);
            client.setEmail(name.replaceAll("\\s+", ".").toLowerCase() + "@example.com");
            client.setIdCard(String.valueOf(generator.nextInt(1000000, 99999999)));
            client.setPhone("+39" + generator.nextLong(1000000000, 9999999999L));
            clients.add(client);
        }
        repo.saveAll(clients);
    }

    private ClientDTO apply(ClientDTO input, Function<Client, Client> f) {
        Client entity = f.apply(input != null ? modelMapper.map(input, Client.class) : null);
        return modelMapper.map(entity, ClientDTO.class);
    }

    private List<ClientDTO> apply(Collection<ClientDTO> input, Function<List<Client>, List<Client>> f) {
        List<Client> entities = f.apply(input != null ? modelMapper.map(input, new TypeToken<List<Client>>() {
        }.getType()) : null);
        return modelMapper.map(entities, new TypeToken<List<ClientDTO>>() {
        }.getType());
    }
}
