package algorithmbasic2020_master.class38;

public class Code01_AppleMinBags {
    /**
     * 小虎去买苹果，商店只提供两种类型的塑料袋，每种类型都有任意数量。
     * 1)能装下6个苹果的袋子
     * 2)能装下8个苹果的袋子
     * 小虎可以自由使用两种袋子来装苹果，但是小虎有强迫症，他要求自己使用的袋子数量必须最少，且使用的每个袋子必须装满。
     * 给定一个正整数N，返回至少使用多少袋子。如果N无法让使用的每个袋子必须装满,返回-1
     */
    public static int minBags(int apple) {
/*		if (apple < 0) {
			return -1;
		}
		int bag8 = (apple >> 3);
		int rest = apple - (bag8 << 3);
		while(bag8 >= 0) {
			// rest 个
			if(rest % 6 ==0) {
				return bag8 + (rest / 6);
			} else {
				bag8--;
				rest += 8;
			}
		}
		return -1;*/
        if (apple < 0) {
            return -1;
        }
        int bag6 = -1;
        //先用 装8个苹果的袋子 去装苹果 看看 剩余几个 能装几个
        int bag8 = apple / 8;
        //求出剩余
        int rest = apple - 8 * bag8;
        /*
         * 只要rest 有剩余 这个剩余不能被6整除 说明 当前 袋子8 和袋子6的组合不可以
         * 那么减少袋子8的数量 再看看剩下的苹果看看能不能被袋子6装上 也就是 苹果数量是否被6整除
         * 如果还是不可以 就再减少袋子8的数量  剩下的苹果看看能不能被袋子6装上
         * 直到 剩余的苹果 >=24 因为苹果数量 大于 24 那么剩余苹果 既可以被袋子8装 也可以 被袋子6装
         * 还有一个条件就是 袋子8的数量 要 大于0
         * */
        while (bag8 >= 0 && rest < 24) {
            int restUse6 = minBagBase6(rest);
            if (restUse6 != -1) {
                bag6 = restUse6;
                break;
            }
            // 8* (--bag8) 表示8 *(bag8 -1)
            rest = apple - 8 * (--bag8);
        }
        return bag6 == -1 ? -1 : bag6 + bag8;
    }

    /*
     * 如果剩余苹果rest可以被装6个苹果的袋子搞定 返回袋子数量
     * 不能搞定 返回-1
     * */
    public static int minBagBase6(int rest) {
        return rest % 6 == 0 ? (rest / 6) : -1;
    }

    public static int minBagAwesome(int apple) {
        if ((apple & 1) != 0) { // 如果是奇数，返回-1
            return -1;//因为奇数肯定不能被8和6整除
        }
        if (apple < 18) {
            return apple == 0 ? 0 : (apple == 6 || apple == 8) ? 1
                    : (apple == 12 || apple == 14 || apple == 16) ? 2 : -1;
        }
        return (apple - 18) / 8 + 3;
    }

    public static void main(String[] args) {
        for (int apple = 1; apple < 200; apple++) {
            System.out.println(apple + " : " + minBags(apple));
        }

    }

}
