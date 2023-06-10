package kr.kro.namohagae.global.util.pagination;

public class Pagination {
    public final Integer blockSize;
    public final Integer pageSize;
    public final Integer pageNo;
    public final Integer countOfObject;
    public final Integer startRowNum;
    public final Integer endRowNum;
    public final Integer countOfPage;
    public final Integer prevPage;
    public final Integer startPage;
    public Integer endPage;
    public Integer nextPage;


    public Pagination(Integer blockSize, Integer pageSize, Integer pageNo, Integer countOfObject) {
        this.blockSize = blockSize;
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.countOfObject = countOfObject;
        this.startRowNum = (this.pageNo-1)*pageSize + 1;
        this.endRowNum = startRowNum+pageSize + 1;
        this.countOfPage = (countOfObject-1)/pageSize + 1;
        this.prevPage =  ((pageNo-1)/blockSize) * blockSize;
        this.startPage = prevPage + 1;
        this.endPage = prevPage + blockSize;
        this.nextPage = endPage+1;
        if(endPage>=countOfPage) {
            this.endPage = countOfPage;
            this.nextPage = 0;
        }

    }



}
