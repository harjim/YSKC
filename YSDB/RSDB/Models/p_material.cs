using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 项目使用物料
    /// </summary>
    public class p_material : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 项目Id
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 物料数据Id
        /// </summary>
        public int materialDataId { get; set; }

        /// <summary>
        /// 项目物料使用量，不能超过物料领用数量
        /// </summary>
        public decimal used { get; set; }

        public int companyId { get; set; }

        /// <summary>
        /// 费用类型
        /// </summary>
        public int rdType { get; set; }

        [Column(TypeName = "decimal(18,2)")]
        public decimal rdAmount { get; set; }

        ///<summary>
        /// 成品数量
        ///</summary> 
        [Column(TypeName = "decimal(18,6)")]
        public decimal finishQuantity { get; set; }

        ///<summary>
        /// 成品单价
        ///</summary>
        [Column(TypeName = "decimal(18,6)")]
        public decimal finishUnitPrice { get; set; }

        ///<summary>
        /// 成品金额
        ///</summary>  
        [Column(TypeName = "decimal(18,6)")]
        public decimal finishAmount { get; set; }
        ///<summary>
        /// 废品数量
        ///</summary>   
        [Column(TypeName = "decimal(18,6)")]
        public decimal wasteQuantity { get; set; }
        ///<summary>
        /// 废品单价
        ///</summary>  
        [Column(TypeName = "decimal(18,6)")]
        public decimal wasteUnitPrice { get; set; }
        ///<summary>
        /// 废品金额
        ///</summary>   
        [Column(TypeName = "decimal(18,6)")]
        public decimal wasteAmount { get; set; }

        ///<summary>
        /// 总产量
        ///</summary>
        [Column(TypeName = "decimal(18,6)")]
        public decimal totalYield { get; set; }

        ///<summary>
        /// 试制量
        ///</summary>
        [Column(TypeName = "decimal(18,6)")]
        public decimal rdYield { get; set; }

        ///<summary>
        /// 损耗率
        ///</summary>
        [Column(TypeName = "decimal(18,6)")]
        public decimal depreciationRatio { get; set; }
    }
}
