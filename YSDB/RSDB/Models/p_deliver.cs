using System;
using System.ComponentModel.DataAnnotations;

namespace RSDB.Models
{
    public class p_deliver : tablebase
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        ///<summary>
        ///项目id
        ///</summary>
        public int projectId { get; set; }
        
        ///<summary>
        ///类型：0 技术，1财务
        ///</summary>
        public int type { get; set; }
        ///<summary>
        ///交付类型：0：立项材料，1：月度轨迹材料，2：年度轨迹材料，3：验收材料，4：财务归集
        ///</summary>
        public int deliverType { get; set; }
        /// <summary>
        /// 0进行中 1通过 2驳回
        /// </summary>
        public int status { get; set; }
        ///<summary>
        /// 节点：0：优赛，10：工厂，20：区域
        ///</summary>
        public int node { get; set; }
        ///<summary>
        /// 年份
        ///</summary>
        public int? year { get; set; }
        ///<summary>
        ///月份
        ///</summary>
        public DateTime month { get; set; }
    }
}