package com.github.facade.bitcoin.services.impl;

import com.github.facade.bitcoin.config.AppConfig;
import com.github.facade.bitcoin.services.IBlockSoChainService;
import com.github.facade.bitcoin.payloads.BlockChainInfo;
import com.github.facade.bitcoin.payloads.BlockHashSoChain;
import com.github.facade.bitcoin.payloads.BlockHeightAdapter;
import com.github.facade.bitcoin.repository.BlockSoChainRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Optional;

public final class BlockSoChainService implements IBlockSoChainService {

    private static final Logger log = LoggerFactory.getLogger(BlockSoChainService.class);

    private final String network;

    private final String url;

    public BlockSoChainService(String network, String url) {
        this.network = network;
        this.url = url;
    }

    @Override
    public BlockChainInfo findBlockChainInfo() {
        BlockSoChainRepository service = AppConfig.getInstance().blockSoChainService(this.url);
        Call<BlockChainInfo> call = service.findBlockChainInfo(this.network);
        try {
            Response<BlockChainInfo> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            }
        } catch (IOException e) {
            log.warn("Enter: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public Optional<BlockHeightAdapter> findBlockHash(Long height) {
        BlockSoChainRepository service = AppConfig.getInstance().blockSoChainService(this.url);
        Call<BlockHashSoChain> call = service.findBlockHash(this.network, height);
        try {
            Response<BlockHashSoChain> response = call.execute();
            if (response.isSuccessful()) {
                return Optional.ofNullable(response.body());
            }
        } catch (IOException e) {
            log.warn("Enter: {}", e.getMessage());
        }
        return Optional.empty();
    }

}
