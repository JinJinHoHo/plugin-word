package pe.pjh.ws.application.service.dataset;

public class Pagination {



    Integer pageNumber = 1;
    Integer pageSize = 20;

    public Pagination() {
    }

    public Pagination(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Pagination(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }
}
