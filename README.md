# Spring Boot Fake Investment

This project simulates a stock market environment with quote generation, automated trading, performance analysis, and auditing capabilities.

## Components

### 1. Quote Generator

- Spring Boot application
- Generates fictional stock quotes for 5 companies
- Uses a simulated algorithm for price fluctuations
- Publishes quotes to a Kafka topic using Spring Kafka
- Implements scheduled tasks for periodic quote generation

### 2. Traders

- Consumes quotes from the Kafka topic
- Implements various trading strategies
- Maintains trader balances and transaction records
- Stores buy/sell transactions in PostgreSQL

### 3. Analyzer

- Provides REST API endpoints for trader performance analysis
- Calculates metrics such as:
  - Total profit percentage per trader
  - Average profit per trade
  - Total number of trades executed
- Retrieves data from PostgreSQL for calculations

### 4. Audit Department

- Integrates Debuziam to read data from the database
- Stores audit data in Kafka topics
- Enables analysis of trader transactions and system behavior

## Technology Stack

- Spring Boot
- Spring Kafka
- PostgreSQL
- Docker Compose

## Setup and Deployment

1. Clone the repository
   
   ```bash
   git clone https://github.com/abdullah-alqubaily/fake-investment.git
2. Open the Project in Intellij and make sure you configure jdk 22
   
3. Build and run the docker compose file (note change the ports if its already takes by anothor container or anothor instance of the same program).
   
   ```bash
   docker compose up --build

4. Run the app in Intellij


## API Documentation

The Analyzer component provides the following REST API endpoints for analyzing trader performance:

### Get All Traders

Retrieves all traders.

- **URL**: `/api/traders`
- **Method**: `GET`
- **Response Format**: JSON
- **Success Response**:
  - **Code**: 200
  - **Content Example**:
    ```json
    {
      "id":5,
      "name":"Technical Analyst",
      "balance":8760.270000000008,
      "totalProfit":-175.20493956043956,
      "initialInvestment":0.0,
      "firstTransaction":true
    }
    ```

### Get All Transactions

Retrieves all transactions.

- **URL**: `/api/transactions`
- **Method**: `GET`
- **Response Format**: JSON
- **Success Response**:
  - **Code**: 200
  - **Content Example**:
    ```json
    {
      "id":1,
      "trader":
          {
            "id":1,
            "name":"Value Investor",
            "balance":2.7299999999971973,
            "totalProfit":0.0,"initialInvestment":0.0,
            "firstTransaction":true
          },
      "symbol":"AAPL",
      "price":98.04,
      "quantity":1,
      "timestamp":"2024-07-31T21:50:58.512426",
      "type":"BUY"
    }
    ```


### Get Trader Performance

Retrieves traders performance.

- **URL**: `/api/analyzer/trader-performance`
- **Method**: `GET`
- **Response Format**: JSON
- **Success Response**:
  - **Code**: 200
  - **Content Example**:
    ```json
    {
      "totalValue":48474.45607177329,
      "averageProfitPerTrade":33.06032201456527,
      "totalProfit":22183.476071773297,
      "balance":26290.979999999992,
      "name":"Mean Reversion Trader",
      "totalTrades":671,
      "profitPercentage":66.7354455646032
    }
    ```
