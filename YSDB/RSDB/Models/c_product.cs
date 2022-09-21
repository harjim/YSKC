using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace RSDB.Models
{
    public class c_product : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }

        ///<summary>
        /// 产品名称
        ///</summary>
        [Required, MaxLength(200)]
        public string pname { get; set; }

        ///<summary>
        /// 产品编码
        ///</summary>
        [Required, MaxLength(20)]
        public string pcode { get; set; }

        ///<summary>
        /// 创建日期（订单产生日期）
        ///</summary>
        public DateTime creationDate { get; set; }

        ///<summary>
        /// 型号
        ///</summary>
        [Required, MaxLength(100)]
        public string model { get; set; }

        ///<summary>
        /// 单位
        ///</summary>
        [Required, MaxLength(10)]
        public string unit { get; set; }

        ///<summary>
        /// 性能参数
        ///</summary>
        [MaxLength(1000)]
        public string parameter { get; set; }

        ///<summary>
        /// 特征用途
        ///</summary>
        [MaxLength(1000)]
        public string features { get; set; }

        ///<summary>
        /// 主要材料
        ///</summary>
        [MaxLength(1000)]
        public string mainRaw { get; set; }

        ///<summary>
        /// 同行对比优劣势（横向对比）
        ///</summary>
        [MaxLength(1000)]
        public string comparison { get; set; }

        ///<summary>
        /// 产量(添加赋0)
        ///</summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal output { get; set; }

        ///<summary>
        /// 产值(添加赋0)
        ///</summary>
        [Required, Column(TypeName = "decimal(18,2)")]
        public decimal outputValue { get; set; }
    }
}
