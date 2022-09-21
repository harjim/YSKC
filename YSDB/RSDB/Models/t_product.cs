using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class t_product
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 区域代码
        /// </summary>
        [MaxLength(30)]
        public string addressCode { get; set; }
        /// <summary>
        /// 产品名称
        /// </summary>
        [Required, MaxLength(50)]
        public string productName { get; set; }

        public int year { get; set; }

        ///<summary>
        /// 项目类型
        ///</summary>
        [Required, MaxLength(50)]
        public string pType { get; set; }
        ///<summary>
        /// 项目级别
        ///</summary>
        [Required, MaxLength(50)]
        public string pLevel { get; set; }
        ///<summary>
        /// 所属政府机构
        ///</summary>
        [MaxLength(50)]

        public string govName { get; set; }

        /// <summary>
        /// 创建人
        /// </summary>
        public int creatorId { get; set; }
        /// <summary>
        /// 更新人
        /// </summary>
        public int lastUpdatorId { get; set; }
        /// <summary>
        /// 创建时间
        /// </summary>
        public DateTime createTime { get; set; }
        public DateTime lastUpdateTime { get; set; }

        ///<summary>
        ///办公地址
        ///</summary>
        [MaxLength(200)]
        public string address { get; set; }
           /// <summary>
        /// 负责人
        /// </summary>
        public int managerId { get; set; }
    }
}