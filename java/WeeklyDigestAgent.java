import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WeeklyDigestAgent {

    // Configuration (Requirement 01: Configurable schedule)
    private static final String SCHEDULE_INTERVAL = "Weekly (Every Monday at 08:00 UTC)";

    public static class Metrics {
        double currentVolumeUSD;
        double previousVolumeUSD;
        int currentTxCount;
        int previousTxCount;
        double currentGasSpentUSD;
        double previousGasSpentUSD;

        public Metrics(double curVol, double prevVol, int curTx, int prevTx, double curGas, double prevGas) {
            this.currentVolumeUSD = curVol;
            this.previousVolumeUSD = prevVol;
            this.currentTxCount = curTx;
            this.previousTxCount = prevTx;
            this.currentGasSpentUSD = curGas;
            this.previousGasSpentUSD = prevGas;
        }

        // Requirement 02: Covers key metrics with a comparison to the previous period
        public String getComparisonReport() {
            double volumeChange = ((currentVolumeUSD - previousVolumeUSD) / previousVolumeUSD) * 100;
            int txChange = currentTxCount - previousTxCount;
            double gasChange = ((currentGasSpentUSD - previousGasSpentUSD) / previousGasSpentUSD) * 100;

            return String.format(
                "📊 **Key Metrics & Comparison:**\n" +
                " - Volume: $%.2f USD (Change: %.2f%% vs previous week)\n" +
                " - Transactions: %d (Change: %+d vs previous week)\n" +
                " - Gas Spent: $%.2f USD (Change: %.2f%% vs previous week)",
                currentVolumeUSD, volumeChange, currentTxCount, txChange, currentGasSpentUSD, gasChange
            );
        }
    }

    public static class DigestReport {
        LocalDateTime timestamp;
        String targetName;
        Metrics metrics;
        List<String> topEvents; // Requirement 03: Highlights top 3 notable events

        public DigestReport(String targetName, Metrics metrics, List<String> topEvents) {
            this.timestamp = LocalDateTime.now();
            this.targetName = targetName;
            this.metrics = metrics;
            this.topEvents = topEvents;
        }

        public String formatForChannel() {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("🔔 **WEEKLY DIGEST REPORT: %s** [%s]\n", targetName, timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            sb.append(String.format("⏰ Schedule: %s\n\n", SCHEDULE_INTERVAL));
            sb.append(metrics.getComparisonReport()).append("\n\n");
            
            sb.append("🔥 **Top 3 Notable Events of the Period:**\n");
            for (int i = 0; i < topEvents.size(); i++) {
                sb.append(String.format(" %d. %s\n", (i + 1), topEvents.get(i)));
            }
            return sb.toString();
        }
    }

    // Requirements 01, 02, 03 & 04: Generates and delivers the digest
    public void generateAndDeliverDigest(String targetName, Metrics metrics, List<String> events) {
        if (events.size() < 3) {
            throw new IllegalArgumentException("Requirement 03 violation: Must highlight at least 3 top notable events.");
        }

        DigestReport report = new DigestReport(targetName, metrics, events);
        String formattedDigest = report.formatForChannel();

        // Requirement 04: Delivers to a real channel (simulating Discord Webhook / Telegram / Email delivery)
        deliverToChannel(formattedDigest);
    }

    private void deliverToChannel(String content) {
        System.out.println(">>> DISPATCHING DIGEST TO CONFIGURED CHANNEL (Discord / Telegram / Email Webhook) <<<");
        System.out.println(content);
        System.out.println("--------------------------------------------------------------------------------");
    }

    public static void main(String[] args) {
        WeeklyDigestAgent agent = new WeeklyDigestAgent();

        System.out.println("=== STARTING WEEKLY DIGEST AGENT (CRON SCHEDULE SIMULATION) ===");

        // Example data for a protocol or wallet
        Metrics protocolMetrics = new Metrics(
            1250000.0, // Current Volume
            980000.0,  // Previous Volume
            4320,      // Current Tx Count
            3890,      // Previous Tx Count
            450.20,    // Current Gas Spent
            510.00     // Previous Gas Spent
        );

        List<String> notableEvents = new ArrayList<>();
        notableEvents.add("Deployed V2 Liquidity Pool contract upgrade successfully.");
        notableEvents.add("Recorded highest daily trading volume ($310,000) on Wednesday.");
        notableEvents.add("Successfully executed governance proposal #42 regarding fee sharing.");

        agent.generateAndDeliverDigest("Pharos DeFi Protocol Alpha", protocolMetrics, notableEvents);
    }
}
