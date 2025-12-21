package org.volumteerhub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryDto {
    private List<DashboardEventDto> recentEvents;        // Mới công bố
    private List<DashboardEventDto> eventsWithNewPosts;  // Có tin bài mới
    private List<DashboardEventDto> trendingEvents;      // Sự kiện thu hút
    
    // Overall statistics
    private Long totalEvents;
    private Long totalUsers;
    private Long totalRegistrations;
    private Long activeEventsCount;
}
