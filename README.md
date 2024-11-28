# Yield-assesment
Job take-home challenge

a) Used technologies
 - Spring 6
 - Boot 3
 - Java 21
 - Maven
 - H2 in-memory database
 
b) Nowadays there is bunch of bots who can give us many theoretical answers for this question but I would like to describe the solution we used in my previous employment and I find it quite sufficient. Each entity must have two specific datetime columns: dt_insert and dt_delete. First column is always updated with actual time (sysdate) and second column has two options:
 - 31/12/9999:  future date which always tells that this record is the only active record.
 - sysdate - 1 second: actual time subtracted by one second.
 If we have first log record we would have:
  29/11/24 00:41:20, 31/12/9999, PENDING, once we update any entity's value (e.g. accreditation status), instead of updating physical record, we update active one and change his dt_delete to minus one second, so we have:
  29/11/24 00:41:20, 29/11/24 00:42:35, PENDING, and at the same time we insert new record with same data + modified properties
  29/11/24 00:42:36, 31/12/9999, CONFIRMED
  Now we have 2 records in table with one active (actual) and one historic record. In order to use only active record, with every query we must use specific clause: 'where sysdate between dt_insert and dt_delete'
 The negative of this solution is progressive amount of records halting database.

c)
 - Load Balancing: Implement load balancers to distribute incoming traffic across multiple servers. This helps to ensure that no single server is overwhelmed and improves the overall reliability and availability of the service.
 - Caching: Use caching mechanisms such as in-memory caches (e.g., Redis, Memcached) to store frequently accessed data. By serving responses from the cache, the number of direct requests to the endpoint can be significantly   reduced, leading to faster response times and reduced load on the servers.
 - Horizontal Scaling: Add more instances of your service to handle increased traffic. This can be done using container orchestration tools like Kubernetes or cloud-based auto-scaling solutions that automatically adjust the number of instances based on traffic patterns.
 - Database Optimization: Optimize database queries and consider using read replicas to handle read-heavy traffic. This can help to offload the primary database and improve query performance.
 - Content Delivery Network (CDN): Use a CDN to distribute static content (such as images, CSS, JavaScript) closer to users. This reduces the load on your servers and speeds up content delivery.
 - Rate Limiting and Throttling: Implement rate limiting and throttling to prevent any single user or IP address from overwhelming the service. This helps to ensure fair usage and protects the service from abusive traffic patterns.
 - Microservices Architecture: Consider breaking down the service into smaller, independently deployable microservices. This can improve scalability and allow different parts of the application to scale independently based on demand.
 - Monitoring and Alerts: Implement robust monitoring and alerting systems to track the performance of your service in real-time. This allows you to quickly identify and address any performance bottlenecks or unusual traffic patterns.