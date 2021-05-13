package com.example.foodinventorydemo.model;

import java.util.ArrayList;
import java.util.List;

public class TransactionSummary {
    public static TransactionSummaryBuilder Builder() {
        return new TransactionSummaryBuilder();
    }

    /**
     * Class for for creating instance of TransactionSummary with config.
     */
    public static class TransactionSummaryBuilder {
        TransactionSummaryConfig config = new TransactionSummaryConfig();

        TransactionSummaryBuilder() {}

        /**
         * Default: true
         * @param val
         * @return Builder for chaining
         */
        public TransactionSummaryBuilder shouldMergeSimilar(boolean val) {
            config.mergeSimilar = val;
            return this;
        }

        /**
         * Completes the build process and creates TransactionSummary instance.
         * @return New TransactionSummary instance with configs.
         */
        public TransactionSummary build() {
            return new TransactionSummary(config);
        }
    }


    /**
     * Internal config class for TransactionSummary
     */
    private static class TransactionSummaryConfig {
        public boolean mergeSimilar = true;

        TransactionSummaryConfig() {}
    }





    private List<ProductBundleTransaction> transactions;
    private TransactionSummaryConfig config;

    private int numAdded = 0;
    private int numRemoved = 0;

    private TransactionSummary(TransactionSummaryConfig config) {
        this.config = config;
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(ProductBundleTransaction transaction) {
        if (config.mergeSimilar) {
            // Attempt to merge similar transactions
            for (ProductBundleTransaction oldTransaction : transactions) {
                if (oldTransaction.getBundle().isSameAs(transaction.getBundle())) {
                    transaction.modifyAmountBy(oldTransaction.getAmount());
                    transactions.remove(oldTransaction);
                    break;
                }
            }
        }
        transactions.add(transaction);
        calculateStats();
    }

    private void calculateStats() {
        int inCnt = 0;
        int outCnt = 0;

        for (ProductBundleTransaction transaction : transactions) {
            if (transaction.getAmount() < 0) outCnt += Math.abs(transaction.getAmount());
            else inCnt += transaction.getAmount();
        }

        numAdded = inCnt;
        numRemoved = outCnt;
    }

    public List<ProductBundleTransaction> getTransactions() {
        return transactions;
    }



    public int getNumAdded() {
        return numAdded;
    }

    public int getNumRemoved() {
        return numRemoved;
    }

}
