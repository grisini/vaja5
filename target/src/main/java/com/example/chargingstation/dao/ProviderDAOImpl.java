package com.example.chargingstation.dao;

import com.example.chargingstation.dao.interfaces.ProviderDAO;
import com.example.chargingstation.vao.Provider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProviderDAOImpl implements ProviderDAO {
    private static ProviderDAOImpl instance;
    private final Map<String, Provider> providers = new HashMap<>();

    // Private constructor for singleton
    private ProviderDAOImpl() {}

    // Singleton instance getter
    public static synchronized ProviderDAOImpl getInstance() {
        if (instance == null) {
            instance = new ProviderDAOImpl();
        }
        return instance;
    }

    @Override
    public void addProvider(Provider provider) {
        if (provider == null) {
            throw new IllegalArgumentException("Provider cannot be null");
        }
        if (providers.containsKey(provider.getId())) {
            throw new IllegalArgumentException("Provider with ID " + provider.getId() + " already exists");
        }
        providers.put(provider.getId(), provider);
    }

    @Override
    public Optional<Provider> getProviderById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        return Optional.ofNullable(providers.get(id));
    }

    @Override
    public List<Provider> getAllProviders() {
        return new ArrayList<>(providers.values());
    }

    @Override
    public void updateProvider(Provider provider) {
        if (provider == null) {
            throw new IllegalArgumentException("Provider cannot be null");
        }
        if (!providers.containsKey(provider.getId())) {
            throw new IllegalArgumentException("Provider with ID " + provider.getId() + " does not exist");
        }
        providers.put(provider.getId(), provider);
    }

    @Override
    public void deleteProvider(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        if (!providers.containsKey(id)) {
            throw new IllegalArgumentException("Provider with ID " + id + " does not exist");
        }
        providers.remove(id);
    }

    @Override
    public List<Provider> findProvidersByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        List<Provider> result = new ArrayList<>();
        for (Provider provider : providers.values()) {
            if (provider.getName().equalsIgnoreCase(name)) {
                result.add(provider);
            }
        }
        return result;
    }
}