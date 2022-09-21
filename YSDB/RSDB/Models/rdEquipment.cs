using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 研发设备
    /// </summary>
    public class rdEquipment : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 年份
        /// </summary>
        public short year { get; set; }
        /// <summary>
        /// 公司ID
        /// </summary>
        public int companyId { get; set; }

        /// <summary>
        /// 资产代码
        /// </summary>
        [Required, MaxLength(100)]
        public string ecode { get; set; }
        /// <summary>
        /// 研发部门
        /// </summary>
        public int rdDeptId { get; set; }

        /// <summary>
        /// 设备类型【30100：仪器，30000：设备，40001：软件摊销】
        /// </summary>
        public int etype { get; set; }
    }
}
