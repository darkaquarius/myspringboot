package boot.repository;

import boot.BaseTest;
import boot.domain.LoanInfo;
import boot.domain.LoanInfo.Contact;
import boot.domain.LoanInfo.Loan;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Created by huishen on 17/9/16.
 *
 */

@WebAppConfiguration
public class LoanInfoRepositoryTest extends BaseTest {

    @Autowired
    private LoanInfoRepository loanInfoRepository;

    @Test
    public void testSave() {
        Contact contact = LoanInfo.Contact.builder()
            .name("李四")
            .age(50)
            .tel("13889578881")
            .build();
        // Contact contact2 = LoanInfo.Contact.builder()
        //     .name("王五")
        //     .age(35)
        //     .tel("18296520819")
        //     .build();

        Loan loan1 = LoanInfo.Loan.builder()
            .amount(new BigDecimal(200.0))
            .rate(0.12)
            .period(6)
            .startDate(LocalDate.now())
            .build();

        LoanInfo loanInfo = LoanInfo.builder()
            .name("陈伟")
            .sex(LoanInfo.Sex.FEMALE)
            .age(30)
            .tel("13888888999")
            .isRich(false)
            .weight(100.2)
            .deposit(new BigDecimal(1000000.0))
            .description("22.6%的年化收益，不仅足以藐视略显屌丝的上证指数，而且还大幅超越目前市场绝大多数信托投资、银行理财、券商资管等高净值产品的年化收益率水平。 据财汇数据统计，截至7月26日，中证医药指数自2005年以来实现了22.6%的年化收益，几乎三倍于同期上证指数的年化收益水平。其中，今年医药板块涨幅超过50%的个股就有29只，涨幅超过30%的个股有58只。 而医药的投资潜力，早已得到机构的高度认同。据wind数据统计，自2008年二季度以来，基金在过去20个季度中持续超配医药类股票。 近期披露的基金二季报显示，在基金持有的重仓股当中，医药股、新兴产业股代替白酒、金融等板块，成为投资的热门之选。其中，基金持有医药生物的总市值高达850亿元。 在政策层面，医药板块的利好持续不断。据国务院日前发布的《深化医药卫生体制改革2013年主要工作安排》，2013年医疗改革将进入攻坚阶段;2013年城镇居民和新农合政府人均补贴同比提升16.7%;并首次提出85%以上的社区卫生服务中心、70%以上的乡镇卫生院、60%以上的社区卫生服务站和村卫生室能够提供中医药服务。 富国基金表示，随着医改的不断深化，医药支出在GDP占比、财政支出占比提高，医药行业的增长空间也会越来越大;中药产业将会得到越来越多的政策扶持。反映到A股市场，将会是医药行业的占比将从目前的约5%逐渐超过10%，医药股票逐渐走向大市值时代。 而随着医药投资热浪来袭，医药主题基金也越来越成为市场上一个炙手可热的基金品种。据证监会新产品募集申请公示表，除在发行的富国医疗保健基金外，目前国内市场至少还有7家基金公司尚在布局医疗主题基金产品。 根据富国基金日前公告，富国医疗保健基金在资产配置上，投资于股票的比例占基金资产的60%-95%，其中投资于医疗保健行业的股票比例不低于非现金基金资产的80%，其业绩比较标准为中证医药卫生指数收益率×80%+中债综合指数收益率×20%。 尽管今年医药行业跑赢大盘约30%，但在富国医疗保健拟任基金经理戴益强看来，医药板块的行情远没有结束。未来在我国老龄化加速等综合因素下，医疗板块有望维持较高的溢价水平。目前，我国医药产业的市值，显然远远无法与我国作为全球第三大医疗市场相匹配。")
            .contact(contact)
            .loans(Arrays.asList(loan1))
            .build();

        loanInfoRepository.save(loanInfo);
    }

    @Test
    public void testUpdateFirst() {
        LoanInfo loanInfo = LoanInfo.builder()
            .name("陈伟1111")
            .age(50)
            .build();
        boolean b = loanInfoRepository.updateFirst(loanInfo);
    }

    @Test
    public void testUpset() {
        LoanInfo loanInfo = LoanInfo
            .builder()
            .name("钱三")
            .age(10)
            .idfas(Arrays.asList("EA3B57AE-227A-4BAF-A539-45BE162584D3", "B87E2B35-2ED1-4E9E-B029-BF955F14B723"))
            .build();
        loanInfoRepository.upset(loanInfo);
    }

    @Test
    public void testFindByContactName() {
        List<LoanInfo> loanInfos =
            loanInfoRepository.findByContact_Name("李四");
    }

    @Test
    public void testFindByLoansRate() {
        List<LoanInfo> loanInfos =
            loanInfoRepository.findByLoansRate(0.12);
    }

    @Test
    public void testFindById() {
        List<LoanInfo> loanInfos =
            loanInfoRepository.findById("59cde6c177c8c74df0e17ab9");
    }

    @Test
    public void testFindAll() {
        List<LoanInfo> all = loanInfoRepository.findAll();
    }

    @Test
    public void testFindByName() {
        LoanInfo loanInfo = loanInfoRepository.findByName("张三");
    }

    @Test
    public void testFindByCustom01() {
        LoanInfo loanInfo = loanInfoRepository.findByCustom01("张三");
    }

    @Test
    public void testFindByCustom02() {
        LoanInfo loanInfo = loanInfoRepository.findByCustom02("张三", 50);
    }

    @Test
    public void testFindByCustom03() {
        List<LoanInfo> loanInfos = loanInfoRepository.findByCustom03("张三", 30);
    }

    @Test
    public void testFindByCustom04() {
        List<LoanInfo> loanInfos = loanInfoRepository.findByCustom04("张三", 30);
    }

    @Test
    public void testFindByCustom05() {
        long ret = loanInfoRepository.findByCustom05("张三", 30);
    }

    @Test
    public void testFindByCustom06() {
        boolean ret = loanInfoRepository.findByCustom06("张三1", 301);
    }

    @Test
    public void testFindByCustom07() {
        List<LoanInfo> loanInfos = loanInfoRepository.findByCustom07(18);
    }
    
    @Test
    public void testFindByCustom08() {
        List<LoanInfo> byCustom08 = loanInfoRepository.findByCustom08(LocalDate.now());
    }

    // @Test
    // public void testfindByCustom09() {
    //     List<LoanInfo> byCustom09 = loanInfoRepository.findByCustom09();
    // }

    @Test
    public void testFindByCustom10() {
        List<LoanInfo> loanInfos = loanInfoRepository.findByCustom10(40);
    }

    @Test
    public void testFindByCustom11() {
        List<LoanInfo> loanInfos = loanInfoRepository.findByCustom11(50, 60);
    }

    // @Test
    // public void testFindByCustom12() {
    //     List<LoanInfo> loanInfos = loanInfoRepository.findByCustom12();
    // }

    @Test
    public void testFind() {
        List<LoanInfo> loanInfos = loanInfoRepository.find(30);
    }

    @Test
    public void testSaveByMongoTemplate() {
        loanInfoRepository.saveByMongoTemplate();
    }

    @Test
    public void testBulkOps() {
        loanInfoRepository.bulkOps();
    }

    @Test
    public void updateByMongoTemplate() {
        loanInfoRepository.updateByMongoTemplate();
    }


}