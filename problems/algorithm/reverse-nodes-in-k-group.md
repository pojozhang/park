# [K个一组翻转链表](https://leetcode-cn.com/problems/reverse-nodes-in-k-group/)

给你一个链表，每k个节点一组进行翻转，请你返回翻转后的链表。
k是一个正整数，它的值小于或等于链表的长度。
如果节点总数不是k的整数倍，那么请将最后剩余的节点保持原有顺序。

**示例 :**

给定这个链表：`1->2->3->4->5`
当 k = 2 时，应当返回: `2->1->4->3->5`
当 k = 3 时，应当返回: `3->2->1->4->5`

**说明 :**

1. 你的算法只能使用常数的额外空间。
2. **你不能只是单纯的改变节点内部的值**，而是需要实际的进行节点交换。

## 实现

- [Java](https://github.com/pojozhang/playground/blob/master/solutions/java/src/main/java/playground/algorithm/ReverseNodesInKGroup.java)
