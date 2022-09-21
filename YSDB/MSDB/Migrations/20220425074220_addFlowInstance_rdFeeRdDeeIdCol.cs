using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addFlowInstance_rdFeeRdDeeIdCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_flowInstance_rdFee_companyId_rsProjectId_rdType_month",
                table: "flowInstance_rdFee");

            migrationBuilder.AddColumn<int>(
                name: "rdFeeId",
                table: "flowInstance_rdFee",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateIndex(
                name: "IX_flowInstance_rdFee_rdFeeId",
                table: "flowInstance_rdFee",
                column: "rdFeeId",
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_flowInstance_rdFee_rdFeeId",
                table: "flowInstance_rdFee");

            migrationBuilder.DropColumn(
                name: "rdFeeId",
                table: "flowInstance_rdFee");

            migrationBuilder.CreateIndex(
                name: "IX_flowInstance_rdFee_companyId_rsProjectId_rdType_month",
                table: "flowInstance_rdFee",
                columns: new[] { "companyId", "rsProjectId", "rdType", "month" },
                unique: true);
        }
    }
}
