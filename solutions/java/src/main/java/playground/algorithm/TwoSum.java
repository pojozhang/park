package playground.algorithm;

public class TwoSum {

    public int[] twoSum(int[] nums, int target) {
        for (int i = 0, length = nums.length; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (nums[i] + nums[j] == target)
                    return new int[]{i, j};
            }
        }
        return null;
    }
}
