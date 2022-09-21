using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addCompanyIdProjectIdMonthDeliverTypeUnique : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<DateTime>(
                name: "month",
                table: "p_deliver",
                nullable: false,
                oldClrType: typeof(DateTime),
                oldNullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_p_deliver_companyId_projectId_month_deliverType",
                table: "p_deliver",
                columns: new[] { "companyId", "projectId", "month", "deliverType" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_p_deliver_companyId_projectId_month_deliverType",
                table: "p_deliver");

            migrationBuilder.AlterColumn<DateTime>(
                name: "month",
                table: "p_deliver",
                nullable: true,
                oldClrType: typeof(DateTime));
        }
    }
}
