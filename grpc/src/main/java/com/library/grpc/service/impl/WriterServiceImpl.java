package com.library.grpc.service.impl;


import com.library.grpc.repository.WriterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WriterServiceImpl   {

    @Autowired
    private WriterRepository writerRepository;


}
