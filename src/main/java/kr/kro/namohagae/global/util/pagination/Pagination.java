package kr.kro.namohagae.global.util.pagination;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Pagination {
    private final Integer blockSize;
    private final Integer pageSize;
    private final Integer pageNo;
    private final Integer countOfObject;
    private final Integer startRowNum;
    private final Integer endRowNum;
    private final Integer countOfPage;
    private final Integer prevPage;
    private final Integer startPage;
    private Integer endPage;
    private Integer nextPage;

    public Pagination(Integer blockSize, Integer pageSize, Integer pageNo, Integer countOfObject) {
        this.blockSize = blockSize;
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.countOfObject = countOfObject;
        this.startRowNum = (this.pageNo-1)*pageSize + 1;
        this.endRowNum = startRowNum+pageSize - 1;
        this.countOfPage = (countOfObject-1)/pageSize + 1;
        this.prevPage =  ((pageNo-1)/blockSize) * blockSize;
        this.startPage = prevPage + 1;
        this.endPage = prevPage + blockSize;
        this.nextPage = endPage+1;
        calculate();
    }
    private void calculate(){
        if(endPage>=countOfPage) {
            this.endPage = countOfPage;
            this.nextPage = 0;
        }
    }
}
