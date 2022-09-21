using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class createP_audit_rdFeeTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "p_audit_rdFee",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    companyId = table.Column<int>(nullable: false),
                    month = table.Column<DateTime>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    rdType = table.Column<int>(nullable: false),
                    moduleId = table.Column<int>(nullable: false),
                    status = table.Column<int>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    suggestion = table.Column<string>(maxLength: 2000, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_audit_rdFee", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_p_audit_rdFee_companyId_month_projectId_rdType",
                table: "p_audit_rdFee",
                columns: new[] { "companyId", "month", "projectId", "rdType" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "p_audit_rdFee");
        }
    }
}
