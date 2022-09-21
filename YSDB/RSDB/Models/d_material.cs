using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 物料清单
    /// </summary>
    public class d_material : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 材料编码
        /// </summary>
        [MaxLength(50)]
        public string mcode { get; set; }
        /// <summary>
        /// 材料名称
        /// </summary>
        [MaxLength(200)]
        public string mname { get; set; }
        /// <summary>
        /// 出库日期
        /// </summary>
        public DateTime acqDate { get; set; }
        /// <summary>
        /// 单价
        /// </summary>
        public decimal unitPrice { get; set; }

        /// <summary>
        /// 数量，考虑会有0.5公斤之类的情况
        /// </summary>
        public decimal quantity { get; set; }

        /// <summary>
        /// 剩余的数量，考虑会有0.5公斤之类的情况
        /// </summary>
        public decimal remainQuantity { get; set; }
        /// <summary>
        /// 单位
        /// </summary>
        [MaxLength(10)]
        public string unit { get; set; }

        public int companyId { get; set; }


        /// <summary>
        /// 备注
        /// </summary>
        [MaxLength(200)]
        public string remark { get; set; }

        /// <summary>
        /// 规格型号
        /// </summary>
        [MaxLength(200)]
        public string specification { get; set; }

        /// <summary>
        /// 部门
        /// </summary>
        public int? deptId { get; set; }
        /// <summary>
        /// 研发部门
        /// </summary>
        public int? rdDeptId { get; set; }

        /// <summary>
        /// 部门
        /// </summary>
        [MaxLength(100)]
        public string deptName { get; set; }

        /// <summary>
        /// 出库单号
        /// </summary>
        [MaxLength(100)]
        public string billNo { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        [MaxLength(30)]
        public string warehouse { get; set; }
        /// <summary>
        /// 制单人
        /// </summary>
        [MaxLength(30)]
        public string biller { get; set; }
        /// <summary>
        /// 审核人
        /// </summary>
        [MaxLength(30)]
        public string auditor { get; set; }
        /// <summary>
        ///记帐人
        /// </summary>
        [MaxLength(30)]
        public string booker { get; set; }
        /// <summary>
        /// 分为材料和试制，材料:20000, 试制:20301
        /// </summary>
        public int type { get; set; }
        /// <summary>
        /// 凭证号
        /// </summary>
        [MaxLength(100)]
        public string accNumber { get; set; }
        /// <summary>
        /// 领料人
        /// </summary>
        [MaxLength(30)]
        public string picker { get; set; }
        /// <summary>
        /// 总金额
        /// </summary>
        [Column(TypeName = "decimal(18,6)")]
        public decimal totalAmount { get; set; }
        /// <summary>
        /// 用途
        /// </summary>
        [MaxLength(200)]
        public string purpose { get; set; }
        /// <summary>
        /// 关联科目id
        /// </summary>
        public int accountTitleId { get; set; }
    }
}
