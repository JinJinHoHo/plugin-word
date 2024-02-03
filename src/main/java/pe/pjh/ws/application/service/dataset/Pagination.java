package pe.pjh.ws.application.service.dataset;

public record Pagination(Integer pageNumber, Integer pageSize) {


    public Pagination() {
        this(1, 40);
    }

    public Pagination(Integer pageNumber) {
        this(pageNumber, 40);
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }
}
