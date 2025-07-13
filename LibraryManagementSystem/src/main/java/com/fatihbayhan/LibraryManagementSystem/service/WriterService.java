package com.fatihbayhan.LibraryManagementSystem.service;

import com.fatihbayhan.librarymanagement.CreateWriterRequest;
import com.fatihbayhan.librarymanagement.DeleteWriterRequest;
import com.fatihbayhan.librarymanagement.DeleteWriterResponse;
import com.fatihbayhan.librarymanagement.GetAllWritersRequest;
import com.fatihbayhan.librarymanagement.GetAllWritersResponse;
import com.fatihbayhan.librarymanagement.GetWriterRequest;
import com.fatihbayhan.librarymanagement.SearchWritersRequest;
import com.fatihbayhan.librarymanagement.SearchWritersResponse;
import com.fatihbayhan.librarymanagement.UpdateWriterRequest;
import com.fatihbayhan.librarymanagement.WriterResponse;

public interface WriterService {
    WriterResponse createWriter(CreateWriterRequest request);
    WriterResponse getWriter(GetWriterRequest request);
    GetAllWritersResponse getAllWriters(GetAllWritersRequest request);
    WriterResponse updateWriter(UpdateWriterRequest request);
    DeleteWriterResponse deleteWriter(DeleteWriterRequest request);
    SearchWritersResponse searchWriters(SearchWritersRequest request);
}
