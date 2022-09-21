using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class ContractTraceabilityCustomerNameCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<int>(
                name: "customerId",
                table: "contractTraceability",
                nullable: true,
                oldClrType: typeof(int));

            migrationBuilder.AddColumn<string>(
                name: "customerName",
                table: "contractTraceability",
                maxLength: 200,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "customerName",
                table: "contractTraceability");

            migrationBuilder.AlterColumn<int>(
                name: "customerId",
                table: "contractTraceability",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);
        }
    }
}
