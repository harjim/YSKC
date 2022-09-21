using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 五险一金配置,按公司默认配置
    /// </summary>
    public class insuranceConfig : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 公司Id
        /// </summary>
        public int companyId { get; set; }


        /// <summary>
        /// 养老
        /// </summary>
        [Column(TypeName = "decimal(6,4)")]
        public decimal endowment { get; set; }
        /// <summary>
        /// 医疗
        /// </summary>
        [Column(TypeName = "decimal(6,4)")]
        public decimal medical { get; set; }
        /// <summary>
        /// 失业
        /// </summary>
        [Column(TypeName = "decimal(6,4)")]
        public decimal unemployment { get; set; }
        /// <summary>
        /// 工伤
        /// </summary>
        [Column(TypeName = "decimal(6,4)")]
        public decimal injury { get; set; }
        /// <summary>
        /// 生育
        /// </summary>
        [Column(TypeName = "decimal(6,4)")]
        public decimal maternity { get; set; }
        /// <summary>
        /// 公积金
        /// </summary>
        [Column(TypeName = "decimal(6,4)")]
        public decimal house { get; set; }


        public int updatorId { get; set; }

        /// <summary>
        /// 养老,公司上交部分
        /// </summary>
        [Column(TypeName = "decimal(6,4)")]
        public decimal endowmentOfCom { get; set; }
        /// <summary>
        /// 医疗,公司上交部分
        /// </summary>
        [Column(TypeName = "decimal(6,4)")]
        public decimal medicalOfCom { get; set; }
        /// <summary>
        /// 失业,公司上交部分
        /// </summary>
        [Column(TypeName = "decimal(6,4)")]
        public decimal unemploymentOfCom { get; set; }
        /// <summary>
        /// 工伤,公司上交部分
        /// </summary>
        [Column(TypeName = "decimal(6,4)")]
        public decimal injuryOfCom { get; set; }
        /// <summary>
        /// 生育,公司上交部分
        /// </summary>
        [Column(TypeName = "decimal(6,4)")]
        public decimal maternityOfCom { get; set; }
        /// <summary>
        /// 公积金,公司上交部分
        /// </summary>
        [Column(TypeName = "decimal(6,4)")]
        public decimal houseOfCom { get; set; }

    }
}
