using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class flowNode : tablebase
    {
        [Key]
        public int id { get; set; }

        ///<summary>
        /// 流程id 1:加计扣除技术部分
        ///</summary>
        public int flowId { get; set; }

        [MaxLength(50), Required]
        public string nodeName { get; set; }
        
        ///<summary>
        /// 节点编号,用来做流程节点的自定义逻辑处理情况,每个流程中,节点标识唯一,比如专利流程.
        ///</summary>
        public int? nodeNumber { get; set; }
        ///<summary>
        /// 节点处理天数,期限.
        ///</summary>
        public int? nodeExpired { get; set; }
        ///<summary>
        ///重复审批自动通过(默认为true)
        ///</summary>
        public bool skip { get; set; }
        public int? prevNode { get; set; }

        public int? nextNode { get; set; }

        ///<summary>
        /// 0开始，1审核，2抄送，3分支
        ///</summary>
        public int type { get; set; }

        ///<summary>
        /// 审核方式：0或签（任意人员审批即通过），1会签（所有人员审批即通过）
        ///</summary>
        public int mode { get; set; }
        
        ///<summary>
        /// 父节点【子流程携带该id,当前流程结束时，通过该id判断流程结束或跳转上一层流程。】
        ///</summary>
        public int? parentId { get; set; }
        ///<summary>
        /// 节点配置（约定为json）
        /// 指定用户配置格式：{"table":"xxxx","innerTable":"yyyy",innerCondition:"t.col=t2.col","resultCol":"xxxx","conditionCol":"instanceId"}
        /// table：查询表 别名为t，主表；
        /// innerTable(可选)：inner join表，别名t2；
        /// innerCondition(可选)：join的条件，有innerTable配置时生效，写innerCondition时，需要指定表别名（t，t2）；
        /// resultCol：结果列（用户条件列），只有t表时，为t表的字段，有t2表，为t2表的字段；
        /// conditionCol：条件列名， 缺省时为instanceId，对应流程id，为t表的查询条件，条件值通过当前流程获取
        /// 拼接示例：
        ///无inner：select yu.id userId,yu.realName from ${table} t inner join ys_user yu on yu.id=t.${resultCol} where t.${conditionCol}=#{instanceId}
        ///有inner：select yu.id userId,yu.realName from ${table} t inner join ${innerTable} t2 on ${innerCondition} 
        ///         inner join ys_user yu on yu.id=t2.${resultCol} where t.${conditionCol}=#{instanceId}
        ///</summary>
        [MaxLength(200)]
        public string configs { get; set; }

    }
}