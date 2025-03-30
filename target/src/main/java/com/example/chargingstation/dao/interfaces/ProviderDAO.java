package com.example.chargingstation.dao.interfaces;

import com.example.chargingstation.vao.Provider;
import java.util.List;
import java.util.Optional;

public interface ProviderDAO {
    void addProvider(Provider provider);
    Optional<Provider> getProviderById(String id);
    List<Provider> getAllProviders();
    void updateProvider(Provider provider);
    void deleteProvider(String id);
    List<Provider> findProvidersByName(String name);
}