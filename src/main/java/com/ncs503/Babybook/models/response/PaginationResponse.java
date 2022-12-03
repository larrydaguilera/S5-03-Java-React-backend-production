package com.ncs503.Babybook.models.response;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaginationResponse {
    List<?> entities;
    String prevPageURI;
    String nextPageURI;
}