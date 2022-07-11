package com.example.wifilocation997.entity;

import com.example.wifilocation997.R;

import java.util.ArrayList;

public class Exhibit {
    public Integer exhibit_number;
    public String exhibit_name;
    public String position;
    public String year;
    public String introduction;
    public String pic_path;
    public int pic;

    public Exhibit() {
        this.exhibit_number = exhibit_number;
        this.exhibit_name = exhibit_name;
        this.position = position;
        this.year = year;
        this.introduction = introduction;
    }

    public Integer getExhibit_number() {
        return exhibit_number;
    }

    public void setExhibit_number(Integer exhibit_number) {
        this.exhibit_number = exhibit_number;
    }

    public String getExhibit_name() {
        return exhibit_name;
    }

    public void setExhibit_name(String exhibit_name) {
        this.exhibit_name = exhibit_name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPic_path() {
        return pic_path;
    }

    public void setPic_path(String pic_path) {
        this.pic_path = pic_path;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    // 声明一个手机商品的名称数组
    private static String[] mNameArray = {
            "《长城颂》", "马克思《布鲁塞尔笔记》", "一大会址", "革命根据地的创建",
            "遵义会议", "党的七大投票箱", "开国大典", "抗美援朝战争",
            "《解放思想，实事求是》", "创办经济特区", "脱贫攻坚", "火神山雷神山医院"
    };

    private static String[] mPositionArray = {
            "1号展厅", "2号展厅", "2号展厅", "2号展厅",
            "2号展厅", "2号展厅", "2号展厅", "3号展厅",
            "3号展厅", "4号展厅", "4号展厅", "4号展厅"
    };

    // 声明展品的类型数组
    private static String[] mYearArray = {
            "巨幅漆画", "笔记手稿", "复原场景", "展板",
            "复原场景", "投票箱", "影像", "模型",
            "讲话提纲", "图片", "数据图表", "模型"
    };

    // 声明一个手机商品的大图数组
    private static int[] mPicArray = {
            R.drawable.changchengsong, R.drawable.blsebj, R.drawable.yida, R.drawable.gmgjd,
            R.drawable.zyhy, R.drawable.tpx, R.drawable.kgdd, R.drawable.kmyc,
            R.drawable.jfsx, R.drawable.jjtq, R.drawable.tpgj, R.drawable.yy
    };

    // 声明一个展品的描述数组
    private static String[] mIntroductionArray = {
            "    穿梭于群山峰峦中的长城，盘旋而上、起伏蜿蜒直入云海深处，气势雄壮地展现中国共产党是时代先锋、民族脊梁。\n" +
                    "    这幅漆画长40米、高15米，由100块漆板拼接而成，作者是清华大学美术学院教授程向军。在中国共产党成立100周年纪念币、《中国共产党成立100周年》纪念封上都有《长城颂》的身影。",

            "    2018年5月3日，马克思《布鲁塞尔笔记》第四笔记本手稿在南京大学美术馆“风云激荡200年——纪念马克思诞辰200周年历史文献展”上首次同公众见面。\n" +
                    "    手稿主体部分写作于历史唯物主义形成的1845年，为马克思对斯托奇、平托、柴尔德等经济学家著作的摘录。手稿还包括1861年至1863年间马克思关于剩余价值的数学算式和家庭账目笔记。据此可推断，马克思在写作《资本论》过程中使用了这一笔记本。",

            "    “红船”，这可能是大多数人谈到中国共产党第一次全国代表大会举办地时的第一印象。事实上，中共一大正式开幕是在上海法租界望志路106号（今兴业路76号），会址设在李书城、李汉俊兄弟住宅，大家围坐在客厅长餐桌四周，室内没有特别布置，陈设简单，气氛庄重。\n" +
                    "    1921年7月30日晚，一大举行第六次会议，原定议题是通过党的纲领和决议，选举中央机构。会议刚开始几分钟，法租界巡捕房密探突然闯入，这次会议被迫中断。王会悟提出：不如到我的家乡嘉兴南湖开会，离上海很近，又易于隐蔽。会议因此得以在南湖的一艘画舫上继续进行。",

            "    三湾改编后，毛泽东带领起义军首先来到井冈山。1927年11月，湘赣边界第一个红色政权——茶陵县工农兵政府成立。1928年2月中旬，江西国民党军队对井冈山地区的进攻被打破。至此，井冈山革命根据地初步建立，边界党的组织也逐步建立起来。\n" +
                    "    井冈山革命根据地的建立，点燃了工农武装割据的星星之火，为中国革命探索出了农村包围城市、武装夺取政权这样一条前人没有走过的正确道路。",


            "    1935年1月，遵义会议在遵义市子尹路的贵州旧军阀柏辉章公馆召开，据遵义会议纪念馆有关负责人介绍，会议召开极为保密，遵义本地没有知情人，当地曾将红军当年召开群众代表大会的天主教堂误认为是遵义会议会址。\n" +
                    "    遵义会议作出了“选举毛泽东同志为中央政治局常委”等重要决定，实际上确立了毛泽东同志在党中央和红军的领导地位。《关于建国以来党的若干历史问题的决议》高度评价遵义会议，称之为“在党的历史上是一个生死攸关的转折点”。",

            "    在抗日战争即将取得胜利的前夜，1945年4月23日至6月11日，中国共产党第七次全国代表大会在延安召开。\n" +
                    "    出席七大的代表共755名，其中正式代表547名，候补代表208名，代表全党121万名党员，分为中直（包括军直系统）、西北、晋绥、晋察冀、晋冀鲁豫、山东、华中和大后方8个代表团。在七大代表中，年龄最大的近70岁，最小的才20岁左右。\n" +
                    "    七大是中国共产党在新民主主义革命时期极其重要的一次、也是最后一次代表大会。这次大会作为“团结的大会、胜利的大会”而载入史册。",

            "    1949年10月1日，中华人民共和国中央人民政府成立典礼在北京天安门广场隆重举行。中华人民共和国诞生了！中国的历史从此翻开了崭新的篇章。\n" +
                    "    下午3时，中央人民政府委员会秘书长林伯渠宣布中央人民政府成立典礼开始。在群众的欢呼声中，毛泽东主席用他那带着湖南口音的洪亮声音，向全世界庄严宣告：“中华人民共和国中央人民政府今天成立了！”顿时，广场上欢声雷动，群情激昂。",

            "    1950年６月，朝鲜内战爆发，美国政府纠集“联合国军”进行武装干涉，并直接威胁到新中国国家安全。10月上旬，中共中央做出了抗美援朝、保家卫国的历史性决策。1953年7月，抗美援朝战争取得伟大胜利。\n" +
                    "    抗美援朝战争的胜利，打破了美帝国主义不可战胜的神话，创造了以弱胜强的范例，极大提高了中国共产党在全国人民心目中的威信，提高了中国人民的民族自信心和民族自豪感，中国的国际地位空前提高，为我国的经济建设和社会发展赢得了一个相对稳定的和平环境。",

            "    党的十一届三中全会实现了新中国成立以来党和国家历史上具有深远意义的伟大转折。\n" +
                    "    1978年11月10日至12月15日召开的中央工作会议，为此作了充分准备。12月13日，邓小平在闭幕会上作了题为《解放思想，实事求是，团结一致向前看》的讲话，指出：“再不实行改革，我们的现代化事业和社会主义事业就会被葬送”。\n" +
                    "    这篇讲话受到与会者的热烈拥护，实际上成为十一届三中全会的主题报告。",

            "    1979年，中共中央作出了创办经济特区的决策。1980年８月，五届全国人大常委会第十五次会议审议批准在深圳、珠海、汕头、厦门设置经济特区。\n" +
                    "    1984年1月24日至2月15日，邓小平先后视察深圳、珠海、厦门，对特区建设的成就给予了充分肯定，并分别为3个经济特区欣然挥笔题词。\n" +
                    "    经济特区向世界展示了中国改革开放的坚定决心，同时也为逐步扩大对外开放和推进经济体制改革提供了丰富经验。",

            "    2021年2月25日，全国脱贫攻坚总结表彰大会在北京人民大会堂隆重举行。\n" +
                    "    经过全党全国各族人民共同努力，在迎来中国共产党成立一百周年的重要时刻，我国脱贫攻坚战取得了全面胜利，现行标准下9899万农村贫困人口全部脱贫，832个贫困县全部摘帽，12.8万个贫困村全部出列，区域性整体贫困得到解决，完成了消除绝对贫困的艰巨任务，创造了又一个彪炳史册的人间奇迹。",

            "    2019年末暴发的新冠肺炎疫情，是百年来全球发生的最严重的传染病大流行，是新中国成立以来我国遭遇的传播速度最快、感染范围最广、防控难度最大的重大突发公共卫生事件。\n" +
                    "    武汉和湖北是疫情防控阻击战的主战场，为收治患者，火神山医院和雷神山医院仅用10多天时间就先后建成。\n" +
                    "    中国共产党坚持人民至上、生命至上，带领人民打响疫情防控的人民战争、总体战、阻击战，夺取了全国抗疫斗争重大战略成果。",

    };

    // 获取默认的手机信息列表
    public static ArrayList<Exhibit> getDefaultList() {
        ArrayList<Exhibit> goodsList = new ArrayList<Exhibit>();
        for (int i = 0; i < mNameArray.length; i++) {
            Exhibit info = new Exhibit();
            info.exhibit_number = i;
            info.exhibit_name = mNameArray[i];
            info.position = mPositionArray[i];
            info.introduction = mIntroductionArray[i];
            info.year = mYearArray[i];
            info.pic = mPicArray[i];
            goodsList.add(info);
        }
        return goodsList;
    }
}