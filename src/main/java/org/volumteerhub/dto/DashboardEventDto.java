package org.volumteerhub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.volumteerhub.common.enumeration.EventStatus;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardEventDto {
    private UUID id;
    private String name;
    private String description;
    private String location;
    private EventStatus status;
    private Instant createdAt;
    private Instant approvedAt;
    private Instant startDate;
    private Instant endDate;
    
    // Statistics
    private Integer registeredCount;
    private Integer postsCount;
    private Integer likesCount;
    private Integer recentPostsCount;
    private Instant latestPostTime;
    
    // Trending score (calculated)
    private Double trendingScore;
}
