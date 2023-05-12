import com.hankcs.hanlp.HanLP;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PageRankTest {

    @Test
    void PageRank() {

        String text = "自然语言处理因何而发生?就我个人来说，自然语言处理对我的意义，不能不说非常重大。 吕凯特曾经说过，生命不可能有两次，但许多人连一次也不善于度过。我希望诸位也能好好地体会这句话。 生活中，若自然语言处理出现了，我们就不得不考虑它出现了的事实。 一般来讲，我们都必须务必慎重的考虑考虑。 了解清楚自然语言处理到底是一种怎么样的存在，是解决一切问题的关键。 自然语言处理的发生，到底需要如何做到，不自然语言处理的发生，又会如何产生。 每个人都不得不面对这些问题。 在面对这种问题时， 带着这些问题，我们来审视一下自然语言处理。 我认为， 自然语言处理，发生了会如何，不发生又会如何。 经过上述讨论。";
        System.out.println(text);
        System.out.println("***********");
        List<String> keywordstrlist = HanLP.extractKeyword(text, 100);
        System.out.println(keywordstrlist.toString());
    }
}
