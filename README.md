# Pharos Weekly Digest Agent

An intelligent reporting agent developed in **Java** for the Pharos Agent Arena designed to produce periodic activity summaries for wallets or protocols[cite: 8].

## 📋 Requirements Met

1. **Configurable Cron Schedule (Req. 01):** Operates on a scheduled cron interval (weekly by default, fully configurable)[cite: 8].
2. **Key Metrics & Period Comparison (Req. 02):** Covers fundamental metrics (volume, transactions, gas fees) alongside percentage comparisons against the previous period[cite: 8].
3. **Top Notable Events (Req. 03):** Highlights the 3 most significant events or milestones achieved during the period[cite: 8].
4. **Real Channel Delivery (Req. 04):** Formats and dispatches reports directly to communication channels like Telegram, Discord, or e-mail webhooks[cite: 8].

## 🚀 How to Run

1. Certifique-se de ter o **Java JDK** instalado.
2. Compile o ficheiro:
   ```bash
   javac WeeklyDigestAgent.java
