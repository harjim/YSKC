using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 铭牌表
    /// </summary>
    public class t_nameplate : tablebase
    {
        [Key]
        public int id { get; set; }

        public int companyId { get; set; }

        public int investmentId { get; set; }

        ///<summary>
        /// 序号（录入）
        ///</summary>
        public int seq { get; set; }

        /// <summary>
        /// 设备名称
        /// </summary>
        [Required, MaxLength(200)]
        public string ename { get; set; }

        /// <summary>
        /// 规格型号
        /// </summary>
        [Required, MaxLength(100)]
        public string emodal { get; set; }

        /// <summary>
        /// 设备制造商
        /// </summary>
        [Required, MaxLength(200)]
        public string manufacturer { get; set; }

        /// <summary>
        /// 出厂日期
        /// </summary>
        public DateTime factoryDate { get; set; }

        /// <summary>
        /// 出厂编号
        /// </summary>
        [Required, MaxLength(100)]
        public string factoryNo { get; set; }

        /// <summary>
        /// 设备安装位置（同步备案位置）
        /// </summary>
        [MaxLength(200)]
        public string setPlace { get; set; }

        ///<summary>
        /// 附件
        ///</summary>
        [MaxLength(200)]
        public string filePath { get; set; }
    }
}
