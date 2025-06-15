# Relog Airline Check-in System Design Document

## 1. Problem Statement

When passengers book tickets with an airline, they complete payment and confirm their reservation. After reservation, they can either do a web check-in to confirm seats or do a physical check-in at the airport before departure.

This system focuses on the web check-in process where passengers:
1. Log in with their PNR
2. Select available seats
3. Receive a boarding pass

The system must handle concurrent seat selection requests and prevent double booking of seats.

## 2. System Requirements

### 2.1 Functional Requirements

- Passengers can log in using their PNR number
- System displays available and unavailable seats
- Passengers can select an available seat
- System prevents multiple passengers from booking the same seat
- System generates a boarding pass after successful seat selection
- System handles concurrent seat selection requests efficiently
- Error handling for already booked seats

### 2.2 Non-Functional Requirements

- High Availability: System should be available 24/7 with minimal downtime
- Durability: Data should persist regardless of system failures
- Scalability: System should handle varying loads efficiently
- Performance: Check-in process should be fast (< 3 seconds)
- Concurrency: System should handle 100+ concurrent users
- Cost-effectiveness: Optimize resource usage
- Security: Protect passenger information

## 3. System Architecture

### 3.1 High-Level Architecture

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│  Web/Mobile │     │   API       │     │  Database   │
│  Interface  │────▶│   Layer     │────▶│  Layer      │
└─────────────┘     └─────────────┘     └─────────────┘
                          │
                          ▼
                    ┌─────────────┐
                    │  Boarding   │
                    │  Pass       │
                    │  Generator  │
                    └─────────────┘
```

### 3.2 Components

#### 3.2.1 Web/Mobile Interface
- Provides UI for passenger interaction
- Displays seat map with availability status
- Handles user input and validation

#### 3.2.2 API Layer
- Authentication and authorization
- Business logic for seat selection
- Concurrency control
- Error handling

#### 3.2.3 Database Layer
- Stores passenger information
- Manages seat allocation status
- Handles transactions and locks

#### 3.2.4 Boarding Pass Generator
- Creates digital boarding passes
- Formats for printing/display

## 4. Database Design

### 4.1 Schema

#### Flights
- flight_id (PK)
- flight_number
- origin
- destination
- departure_time
- arrival_time
- aircraft_id (FK)

#### Aircraft
- aircraft_id (PK)
- model
- total_seats
- configuration_type

#### Seats
- seat_id (PK)
- aircraft_id (FK)
- seat_number
- seat_class
- seat_type (window, aisle, middle)

#### Bookings
- booking_id (PK)
- pnr
- flight_id (FK)
- booking_time
- status

#### Passengers
- passenger_id (PK)
- booking_id (FK)
- first_name
- last_name
- email
- phone

#### CheckIns
- checkin_id (PK)
- passenger_id (FK)
- flight_id (FK)
- seat_id (FK)
- checkin_time
- boarding_pass_issued
- status

### 4.2 Indexing Strategy
- Primary keys on all tables
- Index on PNR for fast lookup
- Index on seat_id and flight_id combination for seat availability checks
- Index on passenger_id for passenger information retrieval

## 5. Concurrency Control

### 5.1 Optimistic Concurrency Control
- Use versioning for seat status
- Check version before update
- Retry on conflict

### 5.2 Pessimistic Concurrency Control
- Row-level locking for seat selection
- Short transaction times to minimize lock duration
- Deadlock detection and prevention

### 5.3 Distributed Locking (for scalability)
- Redis-based distributed locks
- Lock expiration to prevent deadlocks
- Lock acquisition with backoff strategy

## 6. Scaling Strategy

### 6.1 Horizontal Scaling
- Stateless API servers behind load balancer
- Read replicas for database
- Caching layer for frequently accessed data

### 6.2 Vertical Scaling
- Optimize database queries
- Increase resources for database servers during peak times

### 6.3 Caching Strategy
- Cache seat map for each flight
- Invalidate cache on seat selection
- TTL-based cache expiration

## 7. High Availability Design

### 7.1 Database HA
- Primary-replica setup with automatic failover
- Regular backups
- Point-in-time recovery

### 7.2 Application HA
- Multiple application instances
- Health checks and auto-healing
- Blue-green deployment for zero downtime updates

## 8. Data Durability

### 8.1 Transaction Logging
- Write-ahead logs for database operations
- Synchronous replication for critical data

### 8.2 Backup Strategy
- Regular full backups
- Incremental backups
- Geo-redundant storage

## 9. Cost Optimization

### 9.1 Resource Allocation
- Auto-scaling based on demand
- Rightsizing of instances
- Spot instances for non-critical components

### 9.2 Database Optimization
- Connection pooling
- Query optimization
- Appropriate instance sizing

## 10. Capacity Planning

### 10.1 Traffic Patterns
- Peak hours analysis (typically 24-48 hours before flight)
- Seasonal variations
- Special events (holidays, promotions)

### 10.2 Resource Allocation
- Provision for 3x average load
- Auto-scaling triggers at 70% resource utilization
- Reserved capacity for critical flights

## 11. Integration Points

### 11.1 Reservation System
- Retrieve booking details
- Validate PNR
- Check passenger eligibility for web check-in

### 11.2 Departure Control System
- Update check-in status
- Synchronize seat assignments
- Passenger manifest updates

### 11.3 Notification System
- Send boarding pass via email/SMS
- Flight status updates
- Gate change notifications

## 12. Security Considerations

### 12.1 Authentication
- Secure PNR validation
- Rate limiting for login attempts
- Session management

### 12.2 Data Protection
- Encryption for sensitive data
- Compliance with data protection regulations
- Access control and audit logging

## 13. Potential Bottlenecks and Mitigations

### 13.1 Database Contention
- **Bottleneck**: High concurrency on seat selection
- **Mitigation**: Row-level locking, connection pooling, read replicas

### 13.2 Peak Load Handling
- **Bottleneck**: System overload during peak check-in times
- **Mitigation**: Auto-scaling, queue-based processing, load shedding

### 13.3 Network Latency
- **Bottleneck**: Slow response times for distributed systems
- **Mitigation**: CDN, edge caching, optimized API payloads

## 14. Conclusion

The Relog Airline Check-in System is designed to provide a fast, reliable, and concurrent seat selection experience for passengers. By implementing proper concurrency controls, scaling strategies, and high availability measures, the system can handle the requirements efficiently while maintaining data consistency and durability.

The design prioritizes simplicity and elegance while addressing the core challenges of concurrent seat selection and high availability. Each component has a clear responsibility and is designed to scale independently as needed.