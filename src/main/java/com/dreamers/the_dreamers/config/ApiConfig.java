package com.dreamers.the_dreamers.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * API Configuration for The Dreamers Project
 * 
 * This configuration file documents all available API endpoints and their purposes.
 * 
 * Base URL: http://localhost:8080/api
 * 
 * Available Endpoints:
 * 
 * === AUTHENTICATION ===
 * POST   /api/auth/login               - User login with email and password
 * POST   /api/auth/register            - User registration
 * POST   /api/auth/logout              - User logout
 * POST   /api/auth/refresh             - Refresh JWT token
 * GET    /api/auth/me                  - Get current user information
 * 
 * === USER MANAGEMENT ===
 * GET    /api/users                    - Get all users
 * GET    /api/users/{id}               - Get user by ID
 * GET    /api/users/email/{email}      - Get user by email
 * GET    /api/users/username/{username} - Get user by username
 * POST   /api/users                    - Create new user
 * PUT    /api/users/{id}               - Update user
 * DELETE /api/users/{id}               - Delete user
 * GET    /api/users/check-email/{email} - Check if email exists
 * GET    /api/users/check-username/{username} - Check if username exists
 * 
 * === USER DETAILS ===
 * GET    /api/user-details             - Get all user details
 * GET    /api/user-details/{id}        - Get user details by ID
 * GET    /api/user-details/user/{userId} - Get user details by user ID
 * POST   /api/user-details             - Create user details
 * PUT    /api/user-details/{id}        - Update user details
 * DELETE /api/user-details/{id}        - Delete user details
 * 
 * === SOCIAL MEDIA ===
 * GET    /api/social-media             - Get all social media profiles
 * GET    /api/social-media/{id}        - Get social media by ID
 * GET    /api/social-media/user-detail/{userDetailId} - Get social media by user detail
 * POST   /api/social-media             - Create social media profile
 * PUT    /api/social-media/{id}        - Update social media profile
 * DELETE /api/social-media/{id}        - Delete social media profile
 * 
 * === POSTS ===
 * GET    /api/posts                    - Get all posts
 * GET    /api/posts/page               - Get posts with pagination
 * GET    /api/posts/{id}               - Get post by ID
 * GET    /api/posts/author/{authorId}  - Get posts by author
 * GET    /api/posts/author/{authorId}/page - Get posts by author with pagination
 * GET    /api/posts/type/{postType}    - Get posts by type
 * POST   /api/posts                    - Create new post
 * PUT    /api/posts/{id}               - Update post
 * DELETE /api/posts/{id}               - Delete post
 * 
 * === PROJECTS ===
 * GET    /api/projects                 - Get all projects
 * GET    /api/projects/page            - Get projects with pagination
 * GET    /api/projects/{id}            - Get project by ID
 * GET    /api/projects/status/{status} - Get projects by status
 * GET    /api/projects/status/{status}/page - Get projects by status with pagination
 * GET    /api/projects/collected-amount/{amount} - Get projects by collected amount
 * POST   /api/projects                 - Create new project
 * PUT    /api/projects/{id}            - Update project
 * DELETE /api/projects/{id}            - Delete project
 * 
 * === EVENTS ===
 * GET    /api/events                   - Get all events
 * GET    /api/events/page              - Get events with pagination
 * GET    /api/events/{id}              - Get event by ID
 * GET    /api/events/upcoming          - Get upcoming events
 * GET    /api/events/upcoming/page     - Get upcoming events with pagination
 * GET    /api/events/between           - Get events between dates
 * POST   /api/events                   - Create new event
 * PUT    /api/events/{id}              - Update event
 * DELETE /api/events/{id}              - Delete event
 * 
 * === NEWS ===
 * GET    /api/news                     - Get all news
 * GET    /api/news/page                - Get news with pagination
 * GET    /api/news/{id}                - Get news by ID
 * GET    /api/news/recent              - Get recent news
 * GET    /api/news/recent/page         - Get recent news with pagination
 * GET    /api/news/between             - Get news between dates
 * POST   /api/news                     - Create new news
 * PUT    /api/news/{id}                - Update news
 * DELETE /api/news/{id}                - Delete news
 * 
 * === DONATIONS ===
 * GET    /api/donations                - Get all donations
 * GET    /api/donations/page           - Get donations with pagination
 * GET    /api/donations/{id}           - Get donation by ID
 * GET    /api/donations/project/{projectId} - Get donations by project
 * GET    /api/donations/project/{projectId}/page - Get donations by project with pagination
 * GET    /api/donations/donor/{email}  - Get donations by donor email
 * POST   /api/donations                - Create new donation
 * PUT    /api/donations/{id}           - Update donation
 * DELETE /api/donations/{id}           - Delete donation
 * 
 * === PAYMENTS ===
 * GET    /api/payments                 - Get all payments
 * GET    /api/payments/{id}            - Get payment by ID
 * GET    /api/payments/donation/{donationId} - Get payments by donation
 * POST   /api/payments                 - Create new payment
 * PUT    /api/payments/{id}            - Update payment
 * DELETE /api/payments/{id}            - Delete payment
 * 
 * === CONVERSATIONS ===
 * GET    /api/conversations            - Get all conversations
 * GET    /api/conversations/{id}       - Get conversation by ID
 * GET    /api/conversations/participant/{participantId} - Get conversations by participant
 * POST   /api/conversations            - Create new conversation
 * PUT    /api/conversations/{id}       - Update conversation
 * DELETE /api/conversations/{id}       - Delete conversation
 * 
 * === MESSAGES ===
 * GET    /api/messages                 - Get all messages
 * GET    /api/messages/page            - Get messages with pagination
 * GET    /api/messages/{id}            - Get message by ID
 * GET    /api/messages/conversation/{conversationId} - Get messages by conversation
 * GET    /api/messages/conversation/{conversationId}/page - Get messages by conversation with pagination
 * GET    /api/messages/sender/{senderId} - Get messages by sender
 * POST   /api/messages                 - Create new message
 * PUT    /api/messages/{id}            - Update message
 * DELETE /api/messages/{id}            - Delete message
 * 
 * === VIDEO CALLS ===
 * GET    /api/video-calls              - Get all video calls
 * GET    /api/video-calls/{id}         - Get video call by ID
 * GET    /api/video-calls/organizer/{organizerId} - Get video calls by organizer
 * POST   /api/video-calls              - Create new video call
 * PUT    /api/video-calls/{id}         - Update video call
 * DELETE /api/video-calls/{id}         - Delete video call
 * 
 * === CALL PARTICIPANTS ===
 * GET    /api/call-participants        - Get all call participants
 * GET    /api/call-participants/{id}   - Get call participant by ID
 * GET    /api/call-participants/call/{callId} - Get participants by call
 * GET    /api/call-participants/user/{userId} - Get participants by user
 * POST   /api/call-participants        - Create new call participant
 * PUT    /api/call-participants/{id}   - Update call participant
 * DELETE /api/call-participants/{id}   - Delete call participant
 * 
 * === APPLICATIONS ===
 * GET    /api/applications             - Get all applications
 * GET    /api/applications/page        - Get applications with pagination
 * GET    /api/applications/{id}        - Get application by ID
 * GET    /api/applications/user/{userId} - Get applications by user
 * GET    /api/applications/status/{status} - Get applications by status
 * GET    /api/applications/status/{status}/page - Get applications by status with pagination
 * POST   /api/applications             - Create new application
 * PUT    /api/applications/{id}        - Update application
 * DELETE /api/applications/{id}        - Delete application
 * 
 * === NOTIFICATIONS ===
 * GET    /api/notifications            - Get all notifications
 * GET    /api/notifications/page       - Get notifications with pagination
 * GET    /api/notifications/{id}       - Get notification by ID
 * GET    /api/notifications/recipient/{recipientId} - Get notifications by recipient
 * GET    /api/notifications/recipient/{recipientId}/page - Get notifications by recipient with pagination
 * GET    /api/notifications/recipient/{recipientId}/unread - Get unread notifications by recipient
 * POST   /api/notifications            - Create new notification
 * PUT    /api/notifications/{id}       - Update notification
 * DELETE /api/notifications/{id}       - Delete notification
 * 
 * === CHATBOT HISTORY ===
 * GET    /api/chatbot-history          - Get all chatbot history
 * GET    /api/chatbot-history/{id}     - Get chatbot history by ID
 * GET    /api/chatbot-history/user/{userId} - Get chatbot history by user
 * GET    /api/chatbot-history/session/{sessionId} - Get chatbot history by session
 * POST   /api/chatbot-history          - Create new chatbot history
 * PUT    /api/chatbot-history/{id}     - Update chatbot history
 * DELETE /api/chatbot-history/{id}     - Delete chatbot history
 * 
 * === REPORTS ===
 * GET    /api/reports                  - Get all reports
 * GET    /api/reports/page             - Get reports with pagination
 * GET    /api/reports/{id}             - Get report by ID
 * GET    /api/reports/reporter/{reporterId} - Get reports by reporter
 * GET    /api/reports/status/{status}  - Get reports by status
 * GET    /api/reports/status/{status}/page - Get reports by status with pagination
 * POST   /api/reports                  - Create new report
 * PUT    /api/reports/{id}             - Update report
 * DELETE /api/reports/{id}             - Delete report
 * 
 * === INTERNAL DOCUMENTS ===
 * GET    /api/internal-documents       - Get all internal documents
 * GET    /api/internal-documents/{id}  - Get internal document by ID
 * GET    /api/internal-documents/uploader/{uploaderId} - Get documents by uploader
 * POST   /api/internal-documents       - Create new internal document
 * PUT    /api/internal-documents/{id}  - Update internal document
 * DELETE /api/internal-documents/{id}  - Delete internal document
 * 
 * === CLASSIFICATION TABLES ===
 * 
 * === ROLES ===
 * GET    /api/roles                    - Get all roles
 * GET    /api/roles/{id}               - Get role by ID
 * GET    /api/roles/name/{name}        - Get role by name
 * POST   /api/roles                    - Create new role
 * PUT    /api/roles/{id}               - Update role
 * DELETE /api/roles/{id}               - Delete role
 * 
 * === POST TYPES ===
 * GET    /api/post-types               - Get all post types
 * GET    /api/post-types/{id}          - Get post type by ID
 * GET    /api/post-types/name/{typeName} - Get post type by name
 * POST   /api/post-types               - Create new post type
 * PUT    /api/post-types/{id}          - Update post type
 * DELETE /api/post-types/{id}          - Delete post type
 * 
 * === PROJECT STATUSES ===
 * GET    /api/project-statuses         - Get all project statuses
 * GET    /api/project-statuses/{id}    - Get project status by ID
 * GET    /api/project-statuses/name/{statusName} - Get project status by name
 * POST   /api/project-statuses         - Create new project status
 * PUT    /api/project-statuses/{id}    - Update project status
 * DELETE /api/project-statuses/{id}    - Delete project status
 * 
 * === PAYMENT STATUSES ===
 * GET    /api/payment-statuses         - Get all payment statuses
 * GET    /api/payment-statuses/{id}    - Get payment status by ID
 * GET    /api/payment-statuses/name/{statusName} - Get payment status by name
 * POST   /api/payment-statuses         - Create new payment status
 * PUT    /api/payment-statuses/{id}    - Update payment status
 * DELETE /api/payment-statuses/{id}    - Delete payment status
 * 
 * === MESSAGE TYPES ===
 * GET    /api/message-types            - Get all message types
 * GET    /api/message-types/{id}       - Get message type by ID
 * GET    /api/message-types/name/{typeName} - Get message type by name
 * POST   /api/message-types            - Create new message type
 * PUT    /api/message-types/{id}       - Update message type
 * DELETE /api/message-types/{id}       - Delete message type
 * 
 * === CALL STATUSES ===
 * GET    /api/call-statuses            - Get all call statuses
 * GET    /api/call-statuses/{id}       - Get call status by ID
 * GET    /api/call-statuses/name/{statusName} - Get call status by name
 * POST   /api/call-statuses            - Create new call status
 * PUT    /api/call-statuses/{id}       - Update call status
 * DELETE /api/call-statuses/{id}       - Delete call status
 * 
 * === APPLICATION STATUSES ===
 * GET    /api/application-statuses     - Get all application statuses
 * GET    /api/application-statuses/{id} - Get application status by ID
 * GET    /api/application-statuses/name/{statusName} - Get application status by name
 * POST   /api/application-statuses     - Create new application status
 * PUT    /api/application-statuses/{id} - Update application status
 * DELETE /api/application-statuses/{id} - Delete application status
 * 
 * === NOTIFICATION TYPES ===
 * GET    /api/notification-types       - Get all notification types
 * GET    /api/notification-types/{id}  - Get notification type by ID
 * GET    /api/notification-types/name/{typeName} - Get notification type by name
 * POST   /api/notification-types       - Create new notification type
 * PUT    /api/notification-types/{id}  - Update notification type
 * DELETE /api/notification-types/{id}  - Delete notification type
 * 
 * === REPORT TYPES ===
 * GET    /api/report-types             - Get all report types
 * GET    /api/report-types/{id}        - Get report type by ID
 * GET    /api/report-types/name/{typeName} - Get report type by name
 * POST   /api/report-types             - Create new report type
 * PUT    /api/report-types/{id}        - Update report type
 * DELETE /api/report-types/{id}        - Delete report type
 * 
 * === REPORT STATUSES ===
 * GET    /api/report-statuses          - Get all report statuses
 * GET    /api/report-statuses/{id}     - Get report status by ID
 * GET    /api/report-statuses/name/{statusName} - Get report status by name
 * POST   /api/report-statuses          - Create new report status
 * PUT    /api/report-statuses/{id}     - Update report status
 * DELETE /api/report-statuses/{id}     - Delete report status
 * 
 * Query Parameters for Pagination:
 * - page: Page number (default: 0)
 * - size: Page size (default: 10)
 * 
 * Example: GET /api/posts/page?page=0&size=20
 * 
 * CORS Configuration:
 * - All origins are allowed for development
 * - All HTTP methods are allowed
 * - All headers are allowed
 */
@Configuration
public class ApiConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
