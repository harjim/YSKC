using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 项目能源表
    /// </summary>
    public class p_energy : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 项目Id
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 能源数据Id
        /// </summary>
        public int energyDataId { get; set; }

        public int companyId { get; set; }
        [Column(TypeName = "decimal(18,2)")]
        public decimal rdAmount { get; set; }

        ///<summary>
        /// 类型：20100 动力损耗，20200 燃料损耗， 20302 试制动力， 20101 钢铁动力， 20201 钢铁燃料
        ///</summary>
        public int etype { get; set; }

        [Column(TypeName = "decimal(6,2)")]
        public decimal totalHour { get; set; }

        [Column(TypeName = "decimal(6,2)")]
        public decimal rdHour { get; set; }

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


    }
}
