using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 项目设备数据
    /// </summary>
    public class p_equipment : tablebase
    {
        [Key]
        public int id { get; set; }
        /// <summary>
        /// 项目id
        /// </summary>
        public int projectId { get; set; }
        /// <summary>
        /// 设备数据Id
        /// </summary>
        public int equipmentDataId { get; set; }


        /// <summary>
        /// 当前分配研发工时
        /// </summary>
        [Required, MaxLength(200)]
        public string equData { get; set; }

        public int companyId { get; set; }

        /// <summary>
        /// 设备编码
        /// </summary>
        [Required, MaxLength(100)]
        public string ecode { get; set; }
        /// <summary>
        /// 使用月份
        /// </summary>
        public DateTime month { get; set; }

        [Column(TypeName = "decimal(18,2)")]
        public decimal workHours { get; set; }
        ///<summary>
        /// 是否修改过
        ///</summary>
        public bool equEdit { get; set; }

    }
}
