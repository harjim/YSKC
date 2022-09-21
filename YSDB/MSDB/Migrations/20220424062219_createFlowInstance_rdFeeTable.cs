using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createFlowInstance_rdFeeTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "flowInstance_rdFee",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    rsProjectId = table.Column<int>(nullable: false),
                    month = table.Column<DateTime>(nullable: false),
                    rdType = table.Column<int>(nullable: false),
                    instanceId = table.Column<int>(nullable: false),
                    moduleId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_flowInstance_rdFee", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_flowInstance_rdFee_companyId_rsProjectId_rdType_month",
                table: "flowInstance_rdFee",
                columns: new[] { "companyId", "rsProjectId", "rdType", "month" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "flowInstance_rdFee");
        }
    }
}
