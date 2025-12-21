package org.volumteerhub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.volumteerhub.common.enumeration.EventStatus;
import org.volumteerhub.dto.DashboardEventDto;
import org.volumteerhub.dto.DashboardSummaryDto;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Dashboard Controller - Mock data cho testing frontend
 * 
 * Đặc tả: "Xem Dashboard: Xem tổng hợp sự kiện liên quan (mới công bố, có tin bài mới), 
 * sự kiện thu hút (tăng thành viên/trao đổi/like nhanh)."
 */
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DashboardController {

    private final Random random = new Random();

    /**
     * Lấy tổng hợp Dashboard
     */
    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDto> getDashboardSummary() {
        DashboardSummaryDto summary = DashboardSummaryDto.builder()
                .recentEvents(generateRecentEvents())
                .eventsWithNewPosts(generateEventsWithNewPosts())
                .trendingEvents(generateTrendingEvents())
                .totalEvents(156L)
                .totalUsers(2847L)
                .totalRegistrations(8523L)
                .activeEventsCount(42L)
                .build();
        
        return ResponseEntity.ok(summary);
    }

    /**
     * Sự kiện mới công bố (approved gần đây)
     */
    @GetMapping("/recent-approved")
    public ResponseEntity<List<DashboardEventDto>> getRecentlyApprovedEvents(
            @RequestParam(defaultValue = "7") int days,
            @RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(generateRecentEvents().subList(0, Math.min(limit, 5)));
    }

    /**
     * Sự kiện có tin bài mới
     */
    @GetMapping("/with-new-posts")
    public ResponseEntity<List<DashboardEventDto>> getEventsWithNewPosts(
            @RequestParam(defaultValue = "3") int days,
            @RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(generateEventsWithNewPosts().subList(0, Math.min(limit, 5)));
    }

    /**
     * Sự kiện thu hút (trending)
     * - Tăng thành viên nhanh
     * - Tăng trao đổi/bình luận nhanh  
     * - Tăng like nhanh
     */
    @GetMapping("/trending")
    public ResponseEntity<List<DashboardEventDto>> getTrendingEvents(
            @RequestParam(defaultValue = "7") int days,
            @RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(generateTrendingEvents().subList(0, Math.min(limit, 5)));
    }

    // ==================== Mock Data Generators ====================

    private List<DashboardEventDto> generateRecentEvents() {
        List<DashboardEventDto> events = new ArrayList<>();
        String[] eventNames = {
            "Dọn rác bãi biển Mỹ Khê",
            "Hiến máu nhân đạo lần 5",
            "Dạy học cho trẻ em vùng cao",
            "Phát cơm từ thiện",
            "Trồng cây xanh công viên",
            "Hỗ trợ người già neo đơn"
        };
        String[] locations = {
            "Đà Nẵng", "Hà Nội", "Sapa", "TP.HCM", "Huế", "Quảng Nam"
        };
        
        Instant now = Instant.now();
        
        for (int i = 0; i < 5; i++) {
            events.add(DashboardEventDto.builder()
                    .id(UUID.randomUUID())
                    .name(eventNames[i])
                    .description("Mô tả sự kiện " + eventNames[i])
                    .location(locations[i])
                    .status(EventStatus.APPROVED)
                    .createdAt(now.minus(random.nextInt(5) + 1, ChronoUnit.DAYS))
                    .approvedAt(now.minus(random.nextInt(3), ChronoUnit.DAYS))
                    .startDate(now.plus(random.nextInt(14) + 1, ChronoUnit.DAYS))
                    .endDate(now.plus(random.nextInt(14) + 15, ChronoUnit.DAYS))
                    .registeredCount(random.nextInt(50) + 10)
                    .postsCount(random.nextInt(10))
                    .likesCount(random.nextInt(100))
                    .build());
        }
        
        return events;
    }

    private List<DashboardEventDto> generateEventsWithNewPosts() {
        List<DashboardEventDto> events = new ArrayList<>();
        String[] eventNames = {
            "Chạy bộ gây quỹ Marathon",
            "Quyên góp sách vở",
            "Xây nhà tình thương",
            "Thăm trại trẻ mồ côi",
            "Cứu trợ bão lũ miền Trung"
        };
        String[] locations = {
            "Hà Nội", "Đà Nẵng", "Nghệ An", "TP.HCM", "Quảng Bình"
        };
        
        Instant now = Instant.now();
        
        for (int i = 0; i < 5; i++) {
            events.add(DashboardEventDto.builder()
                    .id(UUID.randomUUID())
                    .name(eventNames[i])
                    .description("Mô tả sự kiện " + eventNames[i])
                    .location(locations[i])
                    .status(EventStatus.APPROVED)
                    .createdAt(now.minus(random.nextInt(30) + 7, ChronoUnit.DAYS))
                    .approvedAt(now.minus(random.nextInt(25) + 5, ChronoUnit.DAYS))
                    .startDate(now.plus(random.nextInt(7) + 1, ChronoUnit.DAYS))
                    .endDate(now.plus(random.nextInt(14) + 8, ChronoUnit.DAYS))
                    .registeredCount(random.nextInt(100) + 30)
                    .postsCount(random.nextInt(15) + 5)
                    .likesCount(random.nextInt(150) + 20)
                    .recentPostsCount(random.nextInt(5) + 1)
                    .latestPostTime(now.minus(random.nextInt(48), ChronoUnit.HOURS))
                    .build());
        }
        
        // Sắp xếp theo số bài viết mới giảm dần
        events.sort((a, b) -> b.getRecentPostsCount().compareTo(a.getRecentPostsCount()));
        
        return events;
    }

    private List<DashboardEventDto> generateTrendingEvents() {
        List<DashboardEventDto> events = new ArrayList<>();
        String[] eventNames = {
            "🔥 Chiến dịch Mùa hè xanh 2025",
            "🌟 Tiếp sức mùa thi",
            "💪 Ngày hội tình nguyện quốc gia",
            "❤️ Giọt máu hồng - Tết ấm áp",
            "🌱 Một triệu cây xanh"
        };
        String[] locations = {
            "Toàn quốc", "TP.HCM", "Hà Nội", "Đà Nẵng", "Cần Thơ"
        };
        
        Instant now = Instant.now();
        
        // Trending events có metrics cao
        int[] registeredCounts = {245, 189, 156, 134, 98};
        int[] postsCounts = {67, 45, 38, 29, 22};
        int[] likesCounts = {523, 412, 356, 278, 198};
        double[] trendingScores = {1250.5, 980.3, 756.8, 589.4, 423.1};
        
        for (int i = 0; i < 5; i++) {
            events.add(DashboardEventDto.builder()
                    .id(UUID.randomUUID())
                    .name(eventNames[i])
                    .description("Sự kiện nổi bật: " + eventNames[i])
                    .location(locations[i])
                    .status(EventStatus.APPROVED)
                    .createdAt(now.minus(random.nextInt(14) + 3, ChronoUnit.DAYS))
                    .approvedAt(now.minus(random.nextInt(10) + 2, ChronoUnit.DAYS))
                    .startDate(now.plus(random.nextInt(7) + 1, ChronoUnit.DAYS))
                    .endDate(now.plus(random.nextInt(14) + 8, ChronoUnit.DAYS))
                    .registeredCount(registeredCounts[i])
                    .postsCount(postsCounts[i])
                    .likesCount(likesCounts[i])
                    .trendingScore(trendingScores[i])
                    .build());
        }
        
        return events;
    }
}
