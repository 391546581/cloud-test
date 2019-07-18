package com.blue.rest.compelete.future;

/**
 * @Auther: wangcs
 * @Date: 2019/7/18 16:22
 * @Description:
 */

public class Stock {
    String repo;
    int count;

    public Stock(String repo, int count) {
        this.repo = repo;
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Stock{");
        sb.append("repo='").append(repo).append('\'');
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
