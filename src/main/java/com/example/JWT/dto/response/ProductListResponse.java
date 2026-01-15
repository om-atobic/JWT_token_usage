package com.example.JWT.dto.response;

import java.util.List;

public class ProductListResponse {
    private List<ProductItem> products;
    private int currentPage;
    private int pageSize;
    private long totalItems;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;

    public ProductListResponse() {}

    public ProductListResponse(List<ProductItem> products, int currentPage, int pageSize, long totalItems) {
        this.products = products;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalItems = totalItems;
        this.totalPages = (int) Math.ceil((double) totalItems / pageSize);
        this.hasNext = currentPage < totalPages - 1;
        this.hasPrevious = currentPage > 0;
    }

    public static class ProductItem {
        private String productId;
        private String name;
        private String description;
        private Double price;
        private String category;

        public ProductItem() {}

        public ProductItem(String productId, String name, String description, Double price, String category) {
            this.productId = productId;
            this.name = name;
            this.description = description;
            this.price = price;
            this.category = category;
        }

        public String getProductId() { return productId; }
        public void setProductId(String productId) { this.productId = productId; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }

        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
    }

    public List<ProductItem> getProducts() { return products; }
    public void setProducts(List<ProductItem> products) { this.products = products; }

    public int getCurrentPage() { return currentPage; }
    public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }

    public int getPageSize() { return pageSize; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }

    public long getTotalItems() { return totalItems; }
    public void setTotalItems(long totalItems) { this.totalItems = totalItems; }

    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

    public boolean isHasNext() { return hasNext; }
    public void setHasNext(boolean hasNext) { this.hasNext = hasNext; }

    public boolean isHasPrevious() { return hasPrevious; }
    public void setHasPrevious(boolean hasPrevious) { this.hasPrevious = hasPrevious; }
}